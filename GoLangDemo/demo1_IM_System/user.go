package main

import (
	"fmt"
	"net"
)

type User struct {
	Name       string
	Address    string
	Channel    chan string
	Connection net.Conn
}

/**
*	实例化 user 对象的接口
**/
func NewUser(conn net.Conn) *User {
	user := &User{
		Name:       conn.RemoteAddr().String(),
		Address:    conn.RemoteAddr().String(),
		Channel:    make(chan string),
		Connection: conn,
	}

	go user.ListenMessage()

	return user
}

/**
*	User: 监听 user 绑定的 channel 中的消息
*	如果 channel 中有消息，则发送给客户端
**/
func (user *User) ListenMessage() {
	for {
		msg := <-user.Channel
		user.Connection.Write([]byte(msg + "\n"))
	}
}

/**
*	User: 查询当前在线用户
**/
func (user *User) FindOnlineUsers(server *Server) {
	server.mapLock.Lock()

	for _, user := range server.OnlineUserMap {
		msg := fmt.Sprintf("[%s] %s", user.Address, user.Name)
		user.Channel <- msg
	}

	server.mapLock.Unlock()
}

/**
*	User: 修改用户名
**/
func (user *User) UpdateUserName(newName string, server *Server) {
	server.mapLock.Lock()
	server.OnlineUserMap[newName] = user
	delete(server.OnlineUserMap, user.Name)
	server.mapLock.Unlock()

	user.Name = newName
	user.Channel <- "You have changed your name to " + newName + "\n"
}
