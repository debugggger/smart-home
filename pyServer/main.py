import threading
import time

from dotenv import load_dotenv

from App import MainWindow
from App.MainWindow import MainWindow
from servMqtt import servMqtt
from db import Database

if __name__ == '__main__':
    load_dotenv()

    db = Database()
    app = MainWindow(db)
    smqtt = servMqtt(db)

    mosquittoThread = threading.Thread(target=smqtt.startBroker)
    mosquittoThread.start()
    time.sleep(0.1)
    clientThread = threading.Thread(target=smqtt.startClient)
    clientThread.start()

    app.onConnected()
    app.showDeviceTable()

    app.window.mainloop()



# from tkinter import *
# from tkinter import messagebox
# import pyqrcode
#
# ws = Tk()
# ws.title("PythonGuides")
# ws.config()
#
#
# def generate_QR():
#     global img
#     if len(user_input.get()) != 0:
#         qr = pyqrcode.create(user_input.get())
#         img = BitmapImage(data=qr.xbm(scale=8))
#     else:
#        messagebox.showwarning('warning', 'All Fields are Required!')
#     try:
#         img_lbl.config(image=img)
#     except:
#         pass
#
#
#
# lbl = Label(
#     ws,
#     text="Enter message or URL",
#     bg='#F25252'
# )
# lbl.pack()
#
# user_input = StringVar()
# entry = Entry(
#     ws,
#     textvariable=user_input
# )
# entry.pack(padx=10)
#
# button = Button(
#     ws,
#     text="generate_QR",
#     width=15,
#     command=generate_QR
# )
# button.pack(pady=10)
#
# img_lbl = Label(
#     ws,)
# img_lbl.pack()
#
#
# ws.mainloop()