import React, { useState, useRef, useEffect } from "react";
import PropTypes from "prop-types";
import ChatMessage from "./ChatMessage";

const ChatMessages = ({ messages }) => {
  const bottom = useRef();
  const [count, setCount] = useState(0);

  useEffect(() => {
    setCount((c) => c + 1);
  }, [messages]);

  useEffect(() => {
    if (count === 0) {
      return;
    }
    if (count < 2) {
      bottom?.current?.scrollIntoView(false);
      return;
    }
    bottom?.current?.scrollIntoView({ behavior: "smooth" });
  }, [count]);

  const containerStyle = {
    overflowY: "scroll",
    height: "100%"
  };

  return (
    <div style={containerStyle}>
      {messages.map((m) => {
        return <ChatMessage key={m.id} text={m.text} userSent={m.userSent} />;
      })}
      <div ref={bottom}></div>
    </div>
  );
};

ChatMessages.propTypes = {};

export default ChatMessages;
