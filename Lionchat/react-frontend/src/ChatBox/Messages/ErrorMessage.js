import React, { useState } from "react";
import Logo from "../LionChat_Logo.svg";
import parse from 'html-react-parser'
import styles from './ErrorMessage.module.css'

const ErrorMessage = ({ text }) => {
    return (
        <div className={styles.container}>
            <img
                src={Logo}
                className={styles.logo}
                draggable="false"
                alt="LionChat Logo"
            />
            <div className={styles.messageBubble}>
                <div className={styles.errorMessage}>
                    <label>{text}</label>
                </div>
            </div>
        </div >
    );
};

ErrorMessage.propTypes = {};

export default ErrorMessage;
