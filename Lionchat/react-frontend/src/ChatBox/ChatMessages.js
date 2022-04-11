import React, { useState, useRef, useEffect } from "react";
import PropTypes from "prop-types";
import UserMessage from "./Messages/UserMessage";
import SystemMessage from "./Messages/SystemMessage";
import ErrorMessage from "./Messages/ErrorMessage";
import styles from "./ChatMessages.module.css";
import ReviewMessage from "./Messages/ReviewMessage";

const ChatMessages = ({ messages, handleSendFeedback, handleSendReview }) => {
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
        switch (m.type) {
          case "user":
            return <UserMessage key={m.key} text={m.text} />
          case "system":
            return (
              <SystemMessage
                key={m.key}
                text={m.text}
                id={m.key}
                questionId={m.id}
                helpful={m.helpful}
                handleSendFeedback={handleSendFeedback}
              />
            );
          case "error":
            return <ErrorMessage key={m.key} text={m.text} />
          case "review":
            return (
              <ReviewMessage
                key={m.key}
                text={m.text}
                id={m.key}
                reviewId={m.id}
                score={m.score}
                handleSendReview={handleSendReview}
              />
            );
        }
      })}
      <div ref={bottom}></div>
    </div>
  );
};

ChatMessages.propTypes = {};

export default ChatMessages;
