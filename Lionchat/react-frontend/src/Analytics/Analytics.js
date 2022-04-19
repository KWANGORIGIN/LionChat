import React, { useEffect, useState } from "react";
import styles from "./Analytics.module.css";
import AnalyticsHeader from "./AnalyticsHeader";
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
} from "chart.js";
import { Doughnut, Pie, Bar } from "react-chartjs-2";
import { Modal, Box } from "@mui/material";

ChartJS.register(ArcElement, Tooltip, Legend);
ChartJS.register(CategoryScale, LinearScale, BarElement, Title);

const boxStyle = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "60ch",
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  padding: 2,
  height: "50%",
  overflowY: "scroll",
};

const chartOptions = {
  backgroundColor: [
    "#0e0e5d",
    "#000088",
    "#034c80",
    "#44aaff",
    "#77aaff",
    "#a6ced9",
    "#88ccff",
  ]
    .map((a) => ({ sort: Math.random(), value: a }))
    .sort((a, b) => a.sort - b.sort)
    .map((a) => a.value),
  borderColor: [],
  borderWidth: 1,
};

const Analytics = () => {
  // const [crashReports, setCrashReports] = useState();
  const [totalQuestionsAsked, setTotalQuestionsAsked] = useState(0);
  const [questionsAsked, setQuestionsAsked] = useState([]);
  const [numberQuestionsPerTopic, setNumberQuestionsPerTopic] = useState();
  const [averageRatings, setAverageRatings] = useState("No Ratings");
  const [
    numberMisclassificationsPerTopic,
    setNumberMisclassificationsPerTopic,
  ] = useState();
  const [numberInappropriateQueries, setNumberInappropriateQueries] =
    useState();
  const [inappropriateQueries, setInappropriateQueries] = useState([]);
  const [questionAskedModal, setQuestionAskedModal] = useState(false);
  const [inappropriateQuestionAskedModal, setInappropriateQuestionAskedModal] =
    useState(false);

  useEffect(() => {
    const fetchAnalytics = async () =>
      Promise.all([
        // fetch("/administrative/crashreports"),
        fetch("/administrative/total-questions-asked")
          .then((response) => response.json())
          .then((body) => setTotalQuestionsAsked(body)),
        fetch("/administrative/questions-asked")
          .then((response) => response.json())
          .then((body) => setQuestionsAsked(body)),
        fetch("/administrative/number-questions-per-topic")
          .then((response) => response.json())
          .then((body) => setNumberQuestionsPerTopic(body)),
        fetch("/administrative/average-ratings")
          .then((response) => response.json())
          .then((body) =>
            body === -1
              ? setAverageRatings("No Ratings")
              : setAverageRatings(body)
          ),
        fetch("/administrative/number-misclassifications-per-topic")
          .then((response) => response.json())
          .then((body) => setNumberMisclassificationsPerTopic(body)),
        fetch("/administrative/number-inappropriate-queries")
          .then((response) => response.json())
          .then((body) => setNumberInappropriateQueries(body)),
        fetch("/administrative/inappropriate-queries")
          .then((response) => response.json())
          .then((body) => setInappropriateQueries(body)),
      ]);
    fetchAnalytics();

    // TODO: Websocket to avoid spamming database.
    const interval = setInterval(() => {
      // console.log("fetching analytics")
      fetchAnalytics();
    }, 1000);
    /*
    Take a look at the useEffect Hook. At the end of the Hook, we’re returning a new function. 
    This is the equivalent of the componentWillUnmount lifecycle method in a class-based React component. 
    To learn more about the differences between functional components and class-based components check out this guide

    The useEffect function returns the clearInterval method with the scheduled interval passed into it. 
    As a result, the interval is correctly cleared and no longer triggers every second 
    after the component unmounts from the DOM. */
    return () => clearInterval(interval);
  }, []);

  // useEffect(() => { }, [numberQuestionsPerTopic]);

  return (
    <>
      <AnalyticsHeader />
      <ul className={styles.analyticsList}>
        <li>Total Questions Asked: {totalQuestionsAsked}</li>
        <li>
          Questions Asked:{" "}
          <button onClick={() => setQuestionAskedModal(true)}>View</button>
        </li>
        <Modal
          open={questionAskedModal}
          onClose={() => setQuestionAskedModal(false)}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box sx={boxStyle}>
            <table>
              <thead>
                <tr>
                  <th>Intent</th>
                  <th>Question</th>
                </tr>
              </thead>

              <tbody>
                {questionsAsked?.map((q) => (
                  <tr key={q.key}>
                    <td>{q.intent}</td>
                    <td>{q.question}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </Box>
        </Modal>
        {/* <Doughnut data={numberQuestionsPerTopic} /> */}
        <li>Number Questions Per Topic:</li>
        {numberQuestionsPerTopic && (
          <div className={styles.chartContainer}>
            <div className={styles.doughnut}>
              <Doughnut
                data={{
                  labels: Object.keys(numberQuestionsPerTopic),
                  datasets: [
                    {
                      label: "# Questions Asked",
                      data: Object.values(numberQuestionsPerTopic),
                      ...chartOptions,
                    },
                  ],
                }}
                options={{ maintainAspectRatio: false }}
              />
            </div>
            <div className={styles.bar}>
              <Bar
                data={{
                  labels: Object.keys(numberQuestionsPerTopic),
                  datasets: [
                    {
                      label: "# Questions Asked",
                      data: Object.values(numberQuestionsPerTopic),
                      backgroundColor: "#0e0e5d",
                    },
                  ],
                }}
                options={{ maintainAspectRatio: false }}
              />
            </div>
          </div>
        )}
        <li>
          Average Ratings:{" "}
          {
            /* Don't research how to do this correctly unless you want a world of pain. */
            Math.round((averageRatings + Number.EPSILON) * 100) / 100
          }
          ⭐
        </li>
        <li>
          Number Misclassifications Per Topic:{" "}
          {numberMisclassificationsPerTopic && numberQuestionsPerTopic && (
            <div className={styles.chartContainer}>
              <div className={styles.doughnut}>
                <Doughnut
                  data={{
                    labels: Object.keys(numberMisclassificationsPerTopic),
                    datasets: [
                      {
                        label: "# Questions Asked",
                        data: Object.values(numberMisclassificationsPerTopic),
                        ...chartOptions,
                      },
                    ],
                  }}
                  options={{ maintainAspectRatio: false }}
                />
              </div>
              <div className={styles.bar}>
                <Bar
                  data={{
                    labels: Object.keys(numberQuestionsPerTopic),
                    datasets: [
                      {
                        label: "# Unhelpful Answers",
                        data: Object.values(numberMisclassificationsPerTopic),
                        backgroundColor: "#ff7777",
                      },
                      {
                        label: "# Helpful Answers",
                        data: Object.values(numberQuestionsPerTopic).map(
                          (v, i) =>
                            v -
                            Object.values(numberMisclassificationsPerTopic)[i]
                        ),
                        backgroundColor: "#77ff77",
                      },
                    ],
                  }}
                  options={{ maintainAspectRatio: false }}
                />
              </div>
            </div>
          )}
        </li>
        <li>Number Inappropriate Queries: {numberInappropriateQueries}</li>
        <li>
          Inappropriate Queries:{" "}
          <button onClick={() => setInappropriateQuestionAskedModal(true)}>
            View
          </button>
        </li>
        <Modal
          open={inappropriateQuestionAskedModal}
          onClose={() => setInappropriateQuestionAskedModal(false)}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box sx={boxStyle}>
            <table>
              <thead>
                <tr>
                  <th>User IP</th>
                  <th>Question</th>
                </tr>
              </thead>

              <tbody>
                {inappropriateQueries?.inappropriateData?.map((q) => (
                  <tr key={q.key}>
                    <td>{q.userIp}</td>
                    <td>{q.question}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </Box>
        </Modal>
      </ul>
    </>
  );
};

export default Analytics;
