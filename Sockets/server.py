import socket
import threading

# Config data
host = 'localhost'
port = 9999

# Setting sockets
sock_tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock_tcp.bind((host, port))
sock_tcp.listen(100)

sock_udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock_udp.bind((host, port))

print('Chat server started')

clients = []
clients_addr = []


def client_conn_tcp(connection, address):
    connection.send(bytes("Welcome to the chat!", "utf-8"))
    while True:
        msg = connection.recv(2048)
        msg = str(msg, "utf-8")
        print("[TCP] {}: {}, broadcasting".format(address, msg))
        if msg:
            broadcast_tcp(connection, address, msg)


def broadcast_tcp(connection, address, msg):
    for client in clients:
        if connection != client:
            client.send(bytes("{}: {}".format(address, msg), "utf-8"))


def client_conn_udp(connection):
    while True:
        msg, address = connection.recvfrom(2048)
        msg = str(msg, "utf-8")
        print("[UDP] {}: {}, broadcasting".format(address, msg))
        for client in clients_addr:
            if address != client:
                sock_udp.sendto(bytes("{}: {}".format(address, msg), "utf-8"), client)


def tcp_server():
    while True:
        conn_tcp, addr_tcp = sock_tcp.accept()
        print('Connected tcp and udp:', addr_tcp)
        clients.append(conn_tcp)
        clients_addr.append(addr_tcp)
        client_thread_tcp = threading.Thread(target=client_conn_tcp, args=(conn_tcp, addr_tcp))
        client_thread_udp = threading.Thread(target=client_conn_udp, args=(sock_udp,))
        client_thread_tcp.start()
        client_thread_udp.start()


tcp_server = threading.Thread(target=tcp_server)
tcp_server.start()
