import React from 'react';
import Logo from './LionChat_LogoWhite.svg'
import Close from './LionChat_Close.svg'
import styles from './ChatHeader.module.css'

export default function ChatHeader({ minimize }) {
    return (
        <div className={styles.container}>
            <img src={Logo} className={styles.headerLogo} draggable='false' alt="LionChat Logo" />
            <label>LionChat</label>
            <img src={Close} className={styles.close} onClick={minimize} draggable='false' alt="Close" />
        </div>
    );
}
