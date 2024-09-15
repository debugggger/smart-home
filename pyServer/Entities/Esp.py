class Esp:
    def __init__(self, id: int, name: str, roomId: int):
        self.id: int = id
        self.name: str = name
        self.roomId: int = roomId

    def __repr__(self):
        return f"ESP(id={self.id}, name={self.name}, roomId={self.roomId})"