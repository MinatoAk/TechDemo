package main

import (
	"fmt"
	"net"
)

type Server struct {
	Ip   string
	Port int
}

/**
* 实例化 server 对象的接口
**/
func NewServer(ip string, port int) *Server {
	server := &Server{
		Ip:   ip,
		Port: port,
	}

	return server
}

/**
* Server: 具体处理连接的业务逻辑
**/
func (server *Server) HandleConn(conn net.Conn) {
	fmt.Println("连接建立成功")
}

/**
* Server: 启动服务器
**/
func (server *Server) Start() {
	// 1) 监听端口
	listener, err := net.Listen("tcp", fmt.Sprintf("%s:%d", server.Ip, server.Port))

	if err != nil {
		fmt.Println("[x] net.Listen err:", err)
		return
	}

	// 4) 关闭端口监听
	defer listener.Close()

	for {
		// 2) 接收，成功则表示有一个客户端建立了连接
		// conn 为当前建立成功连接的 socket 套接字
		conn, err := listener.Accept()

		if err != nil {
			fmt.Println("[x] listener.Accept err:", err)
			continue
		}

		// 3) 处理，为了不阻塞，需要开一个 goroutine 去处理
		go server.HandleConn(conn)
	}
}
