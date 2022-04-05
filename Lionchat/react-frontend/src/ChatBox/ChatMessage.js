import React, { useState } from "react";
import PropTypes from "prop-types";
import Logo from "./LionChat_Logo.svg";
import parse from 'html-react-parser'
import styles from './ChatMessage.module.css'
import ChatMessageFooter from "./ChatMessageFooter";

const ChatMessage = ({ id, text, userSent, questionId, helpful, handleSendFeedback }) => {
  const sendFeedback = async (helpful) => {
    const feedbackRequest = { "questionId": questionId, "helpful": helpful }

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

    if (response?.ok) {
      const message = { "key": id, "helpful": helpful }
      handleSendFeedback(message)
    }
  }

  return (
    <div className={styles.container}>
      {!userSent && (
        <img
          src={Logo}
          className={styles.logo}
          draggable="false"
          alt="LionChat Logo"
        />
      )}
      <div className={styles.messageBubble}>
        <div className={userSent ? styles.userMessage : styles.lionchatMessage}>
          <label>{parse(text)}</label>
        </div>
        {!userSent && <ChatMessageFooter helpful={helpful} sendFeedback={sendFeedback} />}
      </div>
    </div >
  );
};

ChatMessage.propTypes = {};

export default ChatMessage;
