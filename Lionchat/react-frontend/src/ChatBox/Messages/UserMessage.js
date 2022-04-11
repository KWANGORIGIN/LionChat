import React from "react";
import styles from './UserMessage.module.css'

const UserMessage = ({ text }) => {
    return (
        <div className={styles.container}>
            <div className={styles.messageBubble}>
                <div className={styles.userMessage}>
                    <label>{text}</label>
                </div>
            </div>
        </div >
    );
};

UserMessage.propTypes = {};

export default UserMessage;
