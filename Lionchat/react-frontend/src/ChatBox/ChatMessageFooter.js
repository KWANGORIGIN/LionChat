import React, { useState } from "react";
import styles from "./ChatMessageFooter.module.css"

//😁🙂😃😢😟😥😖👍👎
const ChatMessageFooter = ({ sendFeedback }) => {
    const [helpfulSelected, setHelpfulSelected] = useState(false);
    const [unhelpfulSelected, setUnhelpfulSelected] = useState(false);

    const setHelpful = (helpful) => {
        // sendFeedback(helpful)

        setHelpfulSelected(helpful)
        setUnhelpfulSelected(!helpful)
    }

    return (
        <div className={styles.footerBubbleContainer}>
            <div className={styles.feedbackBubble}>
                <label className={helpfulSelected ? styles.feedbackEmojiSelected : styles.feedbackEmoji} onClick={() => setHelpful(true)}>🤠</label>
                <label className={unhelpfulSelected ? styles.feedbackEmojiSelected : styles.feedbackEmoji} onClick={() => setHelpful(false)}>🤮</label>
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