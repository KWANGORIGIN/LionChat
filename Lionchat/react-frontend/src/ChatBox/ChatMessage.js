import React from "react";
import PropTypes from "prop-types";
import Logo from "./LionChat_Logo.svg";

const ChatMessage = ({ text, userSent }) => {
  const containerStyle = {
    display: "flex",
    margin: 5,
  };

  const messageStyle = {
    backgroundColor: userSent ? "#66aaff" : "#66aaff",
    color: "white",
    borderRadius: "6px 6px 6px 6px",
    height: "fit-content",
    width: "fit-content",
    padding: 5,
    fontFamily: "Comic Sans MS",
    maxWidth: "55%",
    userSelect: "text",
    marginLeft: userSent ? "auto" : 5,
    marginRight: userSent ? 5 : "auto",
  };

  const logoStyle = {
    height: 40,
    marginLeft: 5,
  };

  return (
    <div style={containerStyle}>
      {!userSent && (
        <img
          src={Logo}
          style={logoStyle}
          draggable="false"
          alt="LionChat Logo"
        />
      )}
      <div style={messageStyle}>{text}</div>
    </div>
  );
};

ChatMessage.propTypes = {};

export default ChatMessage;
