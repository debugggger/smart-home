import tkinter as tk
from tkinter import ttk, CENTER, NSEW, EW, N, GROOVE, S, BOTH, RIGHT, LEFT, TOP, messagebox

from Services.RoomService import RoomService


class RoomMenu:
    def __init__(self, db):
        self.db = db
        self.window = tk.Toplevel()
        width = self.window.winfo_screenwidth()
        height = self.window.winfo_screenheight()
        self.window.geometry("%dx%d" % (width/2, height/2))
        self.window.title("server app room")
        self.devTableFrame = tk.Frame()
        self.roomCount = 0
        self.frameId = 0
        self.canvas = tk.Canvas()

    def showRooms(self):
        self.window.deiconify()

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
        self.packTableInfo(0, "название", "изменить")
        add_button = tk.Button(self.window, text="Новое помещение", command=self.showAddRoom)
        add_button.grid(row=2, column=0, columnspan=3, sticky=S, pady=20)

        back_button = tk.Button(self.window, text="Назад", command=self.window.withdraw)
        back_button.grid(row=0, column=0, columnspan=3, sticky=S, pady=20)

    def showAddRoom(self):
        popup = tk.Toplevel(self.window)
        room_label = tk.Label(popup, text="Помещение:")
        room_label.pack(padx=20)
        room_entry = tk.Entry(popup)
        room_entry.pack(padx=20)
        ok_button = tk.Button(popup, text="OK",
                              command=lambda: self.tableAddRow(room_entry.get(),  popup))
        ok_button.pack(pady=20)

    def FrameWidth(self, event):
        canvas_width = event.width
        self.canvas.itemconfig(self.frameId, width=canvas_width)

    def scrollTableFun(self, event):
        self.canvas.configure(scrollregion=self.canvas.bbox("all"), width=200, height=200)

    def tableAddRow(self, roomName, popup):
        RoomService.addRoom(self.db, str(roomName))
        self.roomCount += 1
        self.packTableInfo(self.roomCount, roomName, 0)
        popup.destroy()

    def packTableInfo(self, row, col1, col2):
        self.devTableFrame.rowconfigure(index=row, weight=1)
        tk.Label(self.devTableFrame, text=col1).grid(row=row, column=0, sticky=N)
        if type(col2) == str:
            tk.Label(self.devTableFrame, text=col2).grid(row=row, column=2, sticky=N)
        else:
            tk.Button(self.devTableFrame, text="-").grid(row=row, column=2, sticky=N)