import React, { useState } from 'react';

const ChatInput = ({ chatInput, handleSendMessage }) => {
    const [hover, setHover] = useState(false)
    const [active, setActive] = useState(false)

    function chatInputKeyPress(e) {
        if (e.code === "Enter") {
            handleSendMessage()
        }
    }

    const containerStyle = {
        display: 'flex',
        height: 36
    }

    const inputStyle = {
        fontSize: 16,
        fontFamily: 'Comic Sans MS',
        flexGrow: 1
    }

    const buttonStyle = ({ hover }) => ({
        border: 'none',
        backgroundColor: active ? '#4444ff' : hover ? '#8888ff' : '#aaaaff',
        width: 'fit-content'
    })

    return (
        <div style={containerStyle}>
            <input style={inputStyle} ref={chatInput} type="text" onKeyPress={chatInputKeyPress} />
            <button
                style={buttonStyle({ hover })}
                onClick={handleSendMessage}
                onPointerOver={() => setHover(true)}
                onPointerOut={() => setHover(false)}
                onPointerDown={() => setActive(true)}
                onPointerUp={() => setActive(false)}
            >Send</button>
        </div>
    )
}

export default ChatInput