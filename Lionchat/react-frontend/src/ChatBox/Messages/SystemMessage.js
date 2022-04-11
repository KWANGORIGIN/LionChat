import React, { useState } from "react";
import Logo from "../LionChat_Logo.svg";
import parse from 'html-react-parser'
import styles from './SystemMessage.module.css'
import SystemMessageFooter from "./SystemMessageFooter";

const SystemMessage = ({ text, id, questionId, helpful, handleSendFeedback }) => {
    const sendFeedback = async (newHelpful) => {
        const oldHelpful = helpful;
        const feedbackRequest = { "questionId": questionId, "helpful": newHelpful }

        const message = { "key": id, "helpful": newHelpful }
        handleSendFeedback(message)

        const response = await fetch(`/chat/update-feedback`,
            {
                method: 'PUT',
                mode: 'cors',
                cache: 'no-cache',
                credentials: 'same-origin',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(feedbackRequest),
            })

        if (!response?.ok) {
            const message = { "key": id, "helpful": oldHelpful }
            handleSendFeedback(message)
        }
    }

    return (
        <div className={styles.container}>
            <img
                src={Logo}
                className={styles.logo}
                draggable="false"
                alt="LionChat Logo"
            />
            <div className={styles.messageBubble}>
                <div className={styles.lionchatMessage}>
                    <label>{parse(text)}</label>
                </div>
                <SystemMessageFooter helpful={helpful} sendFeedback={sendFeedback} />
            </div>
        </div >
    );
};

SystemMessage.propTypes = {};

export default SystemMessage;
