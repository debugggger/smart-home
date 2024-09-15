from Entities.Room import Room

class RoomService:

    @staticmethod
    def addRoom(database, room_name: str) -> None:
        with database.connection.cursor() as cur:
            cur.execute("insert into rooms (name) values (%s) ",
                        (room_name))
