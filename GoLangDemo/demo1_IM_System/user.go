package main

import "net"

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
