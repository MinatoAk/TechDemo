package main

import (
	"fmt"
	"net"
	"sync"
)

type Server struct {
	Ip            string
	Port          int
	OnlineUserMap map[string]*User
	mapLock       sync.RWMutex
	GlobalChannel chan string
}

/**
*	实例化 server 对象的接口
**/
func NewServer(ip string, port int) *Server {
	server := &Server{
		Ip:            ip,
		Port:          port,
		OnlineUserMap: make(map[string]*User),
		GlobalChannel: make(chan string),
	}

	return server
}

/**
*	Server: 将广播 channel 中的消息广播给所有的在线用户
**/
func (server *Server) SendBroadCastMsg() {
	for {
		msg := <-server.GlobalChannel

		server.mapLock.Lock()
		for _, user := range server.OnlineUserMap {
			user.Channel <- msg
		}
		server.mapLock.Unlock()
	}
}

/**
*	Server.HandleConn: 将用户消息扔到广播 channel
**/
func (server *Server) BroadCast(user *User, msg string) {
	sendMsg := fmt.Sprintf("[%s]: %s", user.Name, msg)
	server.GlobalChannel <- sendMsg
}

/**
*	Server: 具体处理连接的业务逻辑
**/
func (server *Server) HandleConn(conn net.Conn) {
	// 1) 用户上线，需要加入到在线用户表中
	user := NewUser(conn)

	server.mapLock.Lock()
	server.OnlineUserMap[user.Name] = user
	server.mapLock.Unlock()

	// 2) 广播用户上线消息
	server.BroadCast(user, "current user online.")
}

/**
*	Server: 启动服务器
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

	// 3) 启动全局广播
	go server.SendBroadCastMsg()

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
