from Entities.Esp import Esp

class EspService:

    @staticmethod
    def addEsp(database, esp_name: str, room_id: int) -> None:
        with database.connection.cursor() as cur:
            cur.execute("insert into Esp (name, room_id) values (%s, %d) ",
                        (esp_name, room_id))
