import React, { useState, useEffect } from "react";
import styles from "./SystemMessageFooter.module.css"

//😁🙂😃😢😟😥😖👍👎
const SystemMessageFooter = ({ helpful, sendFeedback }) => {
    return (
        <div className={styles.footerBubbleContainer}>
            <div className={styles.feedbackBubble}>
                <label className={helpful === true ? styles.feedbackEmojiSelected : styles.feedbackEmoji} onClick={() => sendFeedback(true)}>😀</label>
                <label className={helpful === false ? styles.feedbackEmojiSelected : styles.feedbackEmoji} onClick={() => sendFeedback(false)}>🙁</label>
            </div>
            <div className={styles.bubbleWhiteBackground}>
                <div className={styles.footerBubbleRounded} />
                <div className={styles.smallCurve}>
                    <div className={styles.smallCurveBackground} />
                </div>
            </div>
        </div>
    );
};

SystemMessageFooter.propTypes = {};

export default SystemMessageFooter;