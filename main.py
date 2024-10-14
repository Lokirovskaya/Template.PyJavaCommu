import subprocess as sp
import pyhub

if __name__ == "__main__":
    r = sp.run(["mvn", "clean", "compile", "-q"], shell=True)
    if r.returncode != 0:
        exit(r.returncode)

    hub = pyhub.PyHub(["mvn", "exec:java", "-Dexec.mainClass=Main", "-q"])

    for i in range(10):
        l = [x for x in range(i)]
        print(f"Send: {l}")
        hub.send(l)
        print(hub.receive()["data"])

    hub.close()