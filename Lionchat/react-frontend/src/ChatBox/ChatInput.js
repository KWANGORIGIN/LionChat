import React from 'react';
import styles from './ChatInput.module.css'

const ChatInput = ({ chatInput, handleSendMessage }) => {
    function chatInputKeyPress(e) {
        if (e.code === "Enter") {
            handleSendMessage()
        }

    }
    return (
        <div className={styles.container}>
            <input className={styles.messageBox} ref={chatInput} type="text" onKeyPress={chatInputKeyPress} />
            <button className={styles.send} onClick={handleSendMessage}>Send</button>
        </div>
    )
}

export default ChatInput