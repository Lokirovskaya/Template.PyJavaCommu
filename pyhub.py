try:
    import ujson as json
except ImportError:
    import json
import subprocess as sp


class PyHub:
    def __init__(self, cmd):
        self.process = sp.Popen(
            cmd,
            stdin=sp.PIPE,
            stdout=sp.PIPE,
            shell=True,
        )

    def receive(self):
        raw_json = self.process.stdout.readline()
        return json.loads(raw_json)

    def send(self, obj):
        obj_json = json.dumps(obj, separators=(",", ":")) + "\n"
        self.process.stdin.write(bytes(obj_json, "utf-8"))
        self.process.stdin.flush()

    def close(self):
        self.process.kill()