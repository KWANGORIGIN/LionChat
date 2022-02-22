import React from "react";
import styles from "./ChatMessageFooter.module.css"

//😁🙂😃😢😟😥😖👍👎
const ChatMessageFooter = ({ sendFeedback }) => {
    return (
        <div className={styles.footerBubbleContainer}>
            <div className={styles.feedbackBubble}>
                <label className={styles.feedbackEmoji} onClick={() => sendFeedback(true)}>🤠</label>
                <label className={styles.feedbackEmoji} onClick={() => sendFeedback(false)}>🤮</label>
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

ChatMessageFooter.propTypes = {};

export default ChatMessageFooter;