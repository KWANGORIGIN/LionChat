import React from 'react';
import Logo from './LionChat_LogoWhite.svg'
import styles from './ChatBubble.module.css'

const ChatBubble = ({ maximize }) => {
    return (
        <div className={styles.bubble} onClick={maximize} draggable='false'>
            <img src={Logo} className={styles.logo} draggable='false' alt='LionChat Logo' />
        </div >
    )
}

export default ChatBubble
