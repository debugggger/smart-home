import tkinter as tk
from tkinter import ttk, CENTER, NSEW, EW, N, GROOVE, S, BOTH, RIGHT, LEFT, TOP, messagebox
import pyqrcode

import qrcode

from App.RoomMenu import RoomMenu


class MainWindow:
    def __init__(self, db):
        self.db = db
        self.window = tk.Tk()
        width = self.window.winfo_screenwidth()
        height = self.window.winfo_screenheight()
        self.window.geometry("%dx%d" % (width/2, height/2))
        self.window.title("server app main")
        self.devTableFrame = tk.Frame()
        self.devCount = 0
        self.frameId = 0
        self.canvas = tk.Canvas()

    def roomMenu(self):
        roomMenu = RoomMenu(self.db)
        self.window.withdraw()
        roomMenu.showRooms()
        self.window.deiconify()

    def generateQR(self):
        popupQr = tk.Toplevel(self.window)
        img_lbl = tk.Label(popupQr)
        img_lbl.pack()

        # user_input = tk.StringVar()
        # entry = tk.Entry(
        #     popupQr,
        #     textvariable=user_input
        # )
        # entry.pack(padx=10)
        #
        # user_input.set("ip=192:168:0:17port=1883pass=123456789012345")

        global img
        data = "ip=192:168:0:17port=1883pass=123456789012345"
        if data != 0:
            qr = pyqrcode.create(data)
            img = tk.BitmapImage(data=qr.xbm(scale=8))
        try:
            img_lbl.config(image=img)
        except:
            pass
        ok_button = tk.Button(popupQr, text="OK", command=lambda: popupQr.destroy())
        ok_button.pack(pady=20)

    def onConnected(self):
        self.window.columnconfigure(index=0, weight=1)
        self.window.columnconfigure(index=1, weight=10)
        self.window.columnconfigure(index=2, weight=1)
        self.window.rowconfigure(index=0, weight=1)
        self.window.rowconfigure(index=1, weight=1)
        self.window.rowconfigure(index=2, weight=1)

        qrButton = ttk.Button(text="QR",  command=self.generateQR)
        qrButton.grid(row=0, column=2, sticky=N, pady=20)
        roomButton = ttk.Button(text="Помещения", command=self.roomMenu)
        roomButton.grid(row=0, column=0, sticky=N, pady=20)
        label = tk.Label(text="Подключено")
        label.grid(row=0, column=1, sticky=N, pady=20)


    def add_label(self, parent, text):
        self.label = tk.Label(parent, text=text)
        self.label.pack()

    def tableAddRow(self, espName, room, conDevCount, popup):
        self.db
        self.devCount += 1
        self.packTableInfo(self.devCount, espName, room, conDevCount, 0, 0)
        popup.destroy()

    def packTableInfo(self, row, col1, col2, col3, col4, col5):
        self.devTableFrame.rowconfigure(index=row, weight=1)
        tk.Label(self.devTableFrame, text=col1).grid(row=row, column=0, sticky=N)
        tk.Label(self.devTableFrame, text=col2).grid(row=row, column=1, sticky=N)
        tk.Label(self.devTableFrame, text=col3).grid(row=row, column=2, sticky=N)
        if type(col4) == str:
            tk.Label(self.devTableFrame, text=col4).grid(row=row, column=3, sticky=N)
            tk.Label(self.devTableFrame, text=col5).grid(row=row, column=4, sticky=N)
        else:
            tk.Button(self.devTableFrame, text="+").grid(row=row, column=3, sticky=N)
            tk.Button(self.devTableFrame, text="-").grid(row=row, column=4, sticky=N)

    def FrameWidth(self, event):
        canvas_width = event.width
        self.canvas.itemconfig(self.frameId, width=canvas_width)

    def scrollTableFun(self, event):
        self.canvas.configure(scrollregion=self.canvas.bbox("all"), width=200, height=200)

    def showDeviceTable(self):
        canvaFrame = tk.Frame(self.window, relief=GROOVE, bd=1)
        canvaFrame.grid(row=1, column=0, sticky=NSEW, columnspan=3, padx=20)
        self.canvas = tk.Canvas(canvaFrame)
        self.canvas.pack(side=TOP, fill=BOTH, expand=True)
        self.devTableFrame = tk.Frame(self.canvas)
        scrollBarTable = tk.Scrollbar(canvaFrame, orient="vertical", command=self.canvas.yview)
        self.canvas.configure(yscrollcommand=scrollBarTable.set)
        scrollBarTable.pack(side="right", fill="y")
        self.canvas.pack(side="left")
        self.frameId = self.canvas.create_window((0, 0), window=self.devTableFrame, anchor='nw')

        self.devTableFrame.bind("<Configure>", self.scrollTableFun)
        self.canvas.bind('<Configure>', self.FrameWidth)

        self.devTableFrame.columnconfigure(index=0, weight=1)
        self.devTableFrame.columnconfigure(index=1, weight=1)
        self.devTableFrame.columnconfigure(index=2, weight=1)
        self.devTableFrame.columnconfigure(index=3, weight=1)
        self.devTableFrame.columnconfigure(index=4, weight=1)
        self.packTableInfo(0, "ESP", "помещение", "подключено", "добавить", "изменить")
        add_button = tk.Button(self.window, text="Новое исполнительное устройство", command=self.showAddDevWindow)
        add_button.grid(row=2, column=0, columnspan=3, sticky=S, pady=20)


    def showAddDevWindow(self):
        popup = tk.Toplevel(self.window)
        esp_label = tk.Label(popup, text="ESP:")
        esp_label.pack(padx=20)
        esp_entry = tk.Entry(popup)
        esp_entry.pack(padx=20)
        room_label = tk.Label(popup, text="Помещение:")
        room_label.pack(padx=20)
        room_entry = tk.Entry(popup)
        room_entry.pack(padx=20)
        count_label = tk.Label(popup, text="Количество устройств:")
        count_label.pack(padx=20)
        count_entry = tk.Entry(popup)
        count_entry.pack(padx=20)
        ok_button = tk.Button(popup, text="OK",
                              command=lambda: self.tableAddRow(esp_entry.get(), room_entry.get(), count_entry.get(), popup))
        ok_button.pack(pady=20)

