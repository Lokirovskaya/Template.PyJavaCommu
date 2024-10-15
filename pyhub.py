import ujson
import subprocess as sp


class PyHub:
    def __init__(self, cmd):
        self.process = sp.Popen(
            cmd,
            stdin=sp.PIPE,
            stdout=sp.PIPE,
            stderr=sp.STDOUT,
            shell=True,
        )

    def receive(self):
        raw_json = self.process.stdout.readline()
        try:
            obj = ujson.loads(raw_json)
        except ujson.JSONDecodeError:
            print(f"Corrupted JSON: {raw_json}")
        return obj

    def send(self, obj):
        obj_json = ujson.dumps(obj) + "\n"
        self.process.stdin.write(bytes(obj_json, "utf-8"))
        self.process.stdin.flush()

    def close(self):
        self.process.kill()