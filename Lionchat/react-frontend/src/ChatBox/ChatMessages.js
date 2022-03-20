import React, { useState, useRef, useEffect } from "react";
import PropTypes from "prop-types";
import ChatMessage from "./ChatMessage";
import styles from "./ChatMessages.module.css";

const ChatMessages = ({ messages, handleSendFeedback }) => {
  const bottom = useRef();

  useEffect(() => {
    const parent = bottom?.current?.parentElement;
    const current = parent?.scrollTop;
    const max = parent?.scrollHeight - parent?.clientHeight;

    if (max - current > 1000) {
      bottom?.current?.scrollIntoView(false);
      return;
    }
    bottom?.current?.scrollIntoView({
      behavior: "smooth",
    });
  }, [messages.length]);

  return (
    <div className={styles.container}>
      {messages.map((m) => {
        return (
          <ChatMessage
            key={m.key}
            id={m.key}
            text={m.text}
            userSent={m.userSent}
            questionId={m.id}
            helpful={m.helpful}
            handleSendFeedback={handleSendFeedback}
          />
        );
      })}
      <div ref={bottom}></div>
    </div>
  );
};

ChatMessages.propTypes = {};

export default ChatMessages;
