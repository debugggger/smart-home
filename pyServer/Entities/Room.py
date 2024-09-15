class Room:
    def __init__(self, id: int, name: str):
        self.id: int = id
        self.name: str = name

    def __repr__(self):
        return f"room(id={self.id}, name={self.name})"