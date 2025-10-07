import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

const SOCKET_URL = "http://localhost:8080/ws"; // Your WebSocket endpoint

class WebSocketService {
  constructor() {
    this.client = null;
    this.subscriptions = {};
  }

  connect(onConnectCallback) {
    if (this.client?.connected) return;

    this.client = new Client({
      webSocketFactory: () => new SockJS(SOCKET_URL, null, { withCredentials: true }),
      reconnectDelay: 5000,
      debug: console.log,
      onConnect: () => {
        console.log("Connected to WebSocket");
        onConnectCallback();
      },
      onStompError: console.error,
    });

    this.client.activate();
  }

  subscribeToTasks(companyId, feature, roleId, messageCallback) {
    if (!this.client || !this.client.connected) {
      console.warn("subscribeToTasks aborted: No underlying STOMP connection.");
      return;
    }
    const topic = `/topic/${companyId}/${feature}/${roleId}`;
    if (this.subscriptions[topic]) return; // Prevent duplicate subscriptions
    try {
      const subscription = this.client.subscribe(topic, (message) => {
        const payload = JSON.parse(message.body);
        messageCallback(payload);
      });
      this.subscriptions[topic] = subscription;
    } catch (err) {
      console.error("Error subscribing to topic:", err);
    }
  }

  unsubscribeAll() {
    Object.values(this.subscriptions).forEach(sub => sub.unsubscribe());
    this.subscriptions = {};
  }

  disconnect() {
    this.unsubscribeAll();
    this.client?.deactivate();
    console.log("Disconnected WebSocket");
  }
}

const webSocketService = new WebSocketService();
export default webSocketService;
