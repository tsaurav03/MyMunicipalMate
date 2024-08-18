import { useEffect, useState } from "react";
import Feeds from "./Feeds";
import "../components/FeedComponent.css";

export default function HomeComponent() {
  const [feedarr, setFeedarr] = useState([]);
  const [feedsInstance, setFeedsInstance] = useState(null);

  useEffect(() => {
    const instance = new Feeds();
    setFeedsInstance(instance);

    fetchData(instance);
  }, []);

  const fetchData = (instance) => {
    const arr = instance.getAllFeeds();
    setFeedarr(arr);
  };

  const getStatusClass = (status) => {
    switch (status) {
      case "Finished":
        return "finished";
      case "In Progress":
        return "in-progress";
      case "Not Started":
        return "not-started";
      default:
        return "";
    }
  };

  const updateLikes = (pid) => {
    if (feedsInstance) {
      setFeedarr((prevFeedArr) =>
        prevFeedArr.map((feed) => {
          if (feed.pid === pid) {
            const newLikes = feed.liked ? feed.likes - 1 : feed.likes + 1;
            const updatedFeed = {
              ...feed,
              likes: newLikes,
              liked: !feed.liked,
            };

            if (updatedFeed.liked) {
              feedsInstance.updateIncreaseLikes(pid);
            } else {
              feedsInstance.updateDecreaseLikes(pid);
            }

            return updatedFeed;
          }
          return feed;
        })
      );
    }
  };

  return (
    <div id="home">
      <div id="feeds-box">
        {feedarr.map((feed) => (
          <div key={feed.pid} className="feed-item">
            <div className="icon-container">
              <i className="fas fa-user-circle"></i>&nbsp;
              <span>{" " + feed.pname}</span> <br />
              <br />
            </div>
            <img src={feed.image} alt={feed.pname} className="feed-image" />
            <div className="feed-content">
              <p className="feed-like">
                <i
                  className={`fas fa-heart ${
                    feed.liked ? "liked" : "Not-liked"
                  }`}
                  onClick={() => updateLikes(feed.pid)}
                ></i>
                <span> {" " + feed.likes}</span>
              </p>
              <p className={`feed-status ${getStatusClass(feed.status)}`}>
                <i className="fas fa-exclamation-circle"></i>
                <span>{" " + feed.status}</span>
              </p>
              <p className="feed-location">
                <i className="fas fa-map-marker-alt"></i>
                <span>{" " + feed.location}</span>
              </p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
