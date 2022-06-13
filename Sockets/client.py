import struct
import sys
import socket
import threading

# Config data
host = 'localhost'
port = 9999

group_multicast = '224.1.1.1'
port_multicast = 5007

# Setting sockets
sock_tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock_tcp.connect((host, port))

sock_udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
host_tcp, port_tcp = sock_tcp.getsockname()
sock_udp.bind((host, port_tcp))
sock_udp.connect((host, port))

sock_multicast = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock_multicast.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
sock_multicast.bind(('', port_multicast))

group_binary = socket.inet_aton(group_multicast)
multicast_req = struct.pack('4sL', group_binary, socket.INADDR_ANY)
sock_multicast.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, multicast_req)


# Handling received msg
def client_recv_tcp():
    while True:
        msg = sock_tcp.recv(2048)
        msg = str(msg, "utf-8")
        print("[TCP] {}".format(msg))


def client_recv_udp():
    while True:
        msg = sock_udp.recv(2048)
        msg = str(msg, "utf-8")
        print("[UDP] {}".format(msg))


def client_recv_multicast():
    while True:
        msg = sock_multicast.recv(2048)
        msg = str(msg, "utf-8")
        print("[Multicast] {}".format(msg))


# Creating threads
client_thread = threading.Thread(target=client_recv_tcp)
client_thread.daemon = True
client_thread.start()

client_thread_udp = threading.Thread(target=client_recv_udp)
client_thread_udp.daemon = True
client_thread_udp.start()

client_thread_multicast = threading.Thread(target=client_recv_multicast)
client_thread_multicast.daemon = True
client_thread_multicast.start()

while True:
    send_msg = input()
    if send_msg == "U":
        f = open("ascii-art.txt")
        sock_udp.send(bytes(f.read(), "utf-8"))
    if send_msg.startswith("M"):
        sock_multicast.sendto(bytes(send_msg[2:], "utf-8"), (group_multicast, port_multicast))
    else:
        sock_tcp.send(bytes(send_msg, "utf-8"))
