import React, { useState, useRef, useEffect } from 'react'
import ChatHeader from './ChatHeader'
import ChatMessages from './ChatMessages'
import ChatInput from './ChatInput'
import ChatBubble from './ChatBubble'
import { v4 as uuidv4 } from 'uuid'

const LOCAL_STORAGE_KEY = 'LionChat.Messages'

const ChatBox = () => {
	const [messages, setMessages] = useState([]) // Don't parse local storage in here, it could be null.
	const [minimized, setMinimized] = useState(true)
	const chatInput = useRef()

	useEffect(() => {
		const storedMessages = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY))
		if (storedMessages) {
			setMessages(storedMessages)
		}
	}, [])

	useEffect(() => {
		localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(messages))
	}, [messages])

	async function handleSendMessage() {
		const message = chatInput.current.value
		if (message === 'clear') {
			chatInput.current.value = null
			setMessages([])
			return
		}

		if (message === "") {
			return
		}

		chatInput.current.value = null
		setMessages(previousMessages => {
			return [...previousMessages, { key: uuidv4(), text: message, userSent: true }]
		})


		const question = message

		const response = await fetch(`/chat/askquestion`,
			// TODO: Create an object for this...
			{
				method: 'POST',
				mode: 'cors',
				cache: 'no-cache',
				credentials: 'same-origin',
				headers: {
					'Content-Type': 'application/json',
				},
				// redirect: 'follow',
				body: question,
			})

		const chatResponse = await response.json();

		setMessages(previousMessages => {
			return [...previousMessages, { key: uuidv4(), text: chatResponse.answer, userSent: false, id: chatResponse.questionId }]
		})
	}



	if (minimized) {
		return <ChatBubble maximize={() => setMinimized(false)} />
	}

	const chatBoxStyle = {
		width: 300,
		position: 'fixed',
		bottom: 10,
		right: 10,
		background: '#ffffff',
		height: 400,
		boxShadow: 'rgb(0 0 0 / 20%) 0px 0px 12px',
		display: 'flex',
		flexDirection: 'column',
		userSelect: 'none',
		zIndex: 100000
	}

	return (
		<div style={chatBoxStyle}>
			<ChatHeader minimize={() => setMinimized(true)} />
			<ChatMessages messages={messages} />
			<ChatInput chatInput={chatInput} handleSendMessage={handleSendMessage} />
		</div>
	)
}

export default ChatBox