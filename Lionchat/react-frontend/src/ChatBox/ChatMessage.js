import React, { useState } from "react";
import PropTypes from "prop-types";
import Logo from "./LionChat_Logo.svg";
import parse from 'html-react-parser'
import styles from './ChatMessage.module.css'
import ChatMessageFooter from "./ChatMessageFooter";

const ChatMessage = ({ text, userSent, id }) => {
  const [setId] = useState()

  const sendFeedback = async (helpful) => {
    console.log(`is helpful: ${helpful} id: ${id}`)

    const feedbackRequest = { "questionId": id, "helpful": helpful }

    const response = await fetch(`/chat/feedback`,
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
        body: JSON.stringify(feedbackRequest),
      })

    console.log(response)
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
        {!userSent && <ChatMessageFooter sendFeedback={sendFeedback} />}
      </div>
    </div >
  );
};

ChatMessage.propTypes = {};

export default ChatMessage;
