const express = require("express");
const { MongoClient } = require("mongodb");
const app = express();
const port = 3000;
const url = "mongodb://localhost:27017";
const dbName = "userdb";

app.use(express.json());

// user create
app.post("/users", async (req, res) => {
  const { name, email, phoneNumber } = req.body;

  const client = new MongoClient(url);
  try {
    await client.connect();
    const db = client.db(dbName);
    const user = await db.collection("users").findOne({ _id: phoneNumber });
    if (user) {
      res.status(500).json({ error: "User exists" });
      return;
    }
    const result = await db
      .collection("users")
      .insertOne({ _id: phoneNumber, name, email });
    res.json({ message: "User created" });
  } catch (error) {
    console.error("Error:", error);
    res.status(500).json({ error: "An error occurred" });
  } finally {
    client.close();
  }
});

// get all users
app.get("/users", async (req, res) => {
  const client = new MongoClient(url);
  try {
    await client.connect();
    const db = client.db(dbName);
    const users = await db.collection("users").find().toArray();
    res.json(users);
  } catch (error) {
    console.error("Error:", error);
    res.status(500).json({ error: "An error occurred" });
  } finally {
    client.close();
  }
});

// get a user by phone
app.get("/users/:phoneNumber", async (req, res) => {
  const phoneNumber = req.params.phoneNumber;

  const client = new MongoClient(url);
  try {
    await client.connect();
    const db = client.db(dbName);
    const user = await db.collection("users").findOne({ _id: phoneNumber });
    if (user) {
      res.json(user);
    } else {
      res.status(404).json({ error: "User not found" });
    }
  } catch (error) {
    console.error("Error:", error);
    res.status(500).json({ error: "An error occurred" });
  } finally {
    client.close();
  }
});

// update a user
app.put("/users/:phoneNumber", async (req, res) => {
  const phoneNumber = req.params.phoneNumber;
  const { name, email } = req.body;
  const client = new MongoClient(url);
  try {
    await client.connect();
    const db = client.db(dbName);
    const user = await db.collection("users").findOne({ _id: phoneNumber });
    if (user) {
      const result = await db
        .collection("users")
        .updateOne({ _id: phoneNumber }, { $set: { name, email } });
      res.json({ message: "User updated successfully" });
    } else {
      res.status(500).json({ error: "User not found" });
    }
  } catch (error) {
    console.error("Error:", error);
    res.status(500).json({ error: "An error occurred" });
  } finally {
    client.close();
  }
});

// delete a user phone
app.delete("/users/:phoneNumber", async (req, res) => {
    const phoneNumber = req.params.phoneNumber;
  
    const client = new MongoClient(url);
    try {
      await client.connect();
      const db = client.db(dbName);
  
      // delete all orders with phone number
      await db.collection("orders").deleteMany({ phoneNumber });
      const result = await db.collection("users").deleteOne({ _id: phoneNumber });
      if (result.deletedCount === 1) {
        res.json({ message: "User and orders deleted successfully" });
      } else {
        res.status(404).json({ error: "User not found" });
      }
    } catch (error) {
      console.error("Error:", error);
      res.status(500).json({ error: "An error occurred" });
    } finally {
      client.close();
    }
  });
  

// orders CRUD
function getRandomInteger(length) {
  var min = Math.pow(10, length - 1);
  var max = Math.pow(10, length) - 1;
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

// create order
app.post("/orders", async (req, res) => {
  const { phoneNumber, orderString } = req.body;

  const client = new MongoClient(url);
  try {
    await client.connect();
    const db = client.db(dbName);
    const user = await db.collection("users").findOne({ _id: phoneNumber });
    if (!user) {
      res.status(404).json({ error: "User not found" });
      return;
    }
    var counter = getRandomInteger(5);
    const order = { _id: counter, phoneNumber, orderString };
    await db.collection("orders").insertOne(order);
    res.json({ message: "Order created", counter });
  } catch (error) {
    console.error("Error:", error);
    res.status(500).json({ error: "An error occurred" });
  } finally {
    client.close();
  }
});

// get all orders
app.get("/orders", async (req, res) => {
  const client = new MongoClient(url);
  try {
    await client.connect();
    const db = client.db(dbName);
    const orders = await db.collection("orders").find().toArray();
    res.json(orders);
  } catch (error) {
    console.error("Error:", error);
    res.status(500).json({ error: "An error occurred" });
  } finally {
    client.close();
  }
});

// get all orders of phone number
app.get("/orders/:phoneNumber", async (req, res) => {
  const phoneNumber = req.params.phoneNumber;

  const client = new MongoClient(url);
  try {
    await client.connect();
    const db = client.db(dbName);
    const user = await db.collection("users").findOne({ _id: phoneNumber });
    if (!user) {
      res.status(404).json({ error: "User not found" });
      return;
    }
    const orders = await db
      .collection("orders")
      .find({ phoneNumber })
      .toArray();
    res.json(orders);
  } catch (error) {
    console.error("Error:", error);
    res.status(500).json({ error: "An error occurred" });
  } finally {
    client.close();
  }
});

// update an order
app.put("/orders/:orderCounter", async (req, res) => {
  const orderCounter = parseInt(req.params.orderCounter);
  const { phoneNumber, orderString } = req.body;

  const client = new MongoClient(url);
  try {
    await client.connect();
    const db = client.db(dbName);
    const user = await db.collection("users").findOne({ _id: phoneNumber });
    if (!user) {
      res.status(404).json({ error: "User not found" });
      return;
    }
    console.log(orderCounter);
    const result = await db
      .collection("orders")
      .updateOne(
        { _id: orderCounter},
        { $set: { orderString } }
      );
    if (result.matchedCount === 1) {
      res.json({ message: "Order updated successfully" });
    } else {
      res.status(404).json({ error: "Order not found" });
    }
  } catch (error) {
    console.error("Error:", error);
    res.status(500).json({ error: "An error occurred" });
  } finally {
    client.close();
  }
});

// delete order
app.delete("/orders/:orderCounter", async (req, res) => {
  const orderCounter = parseInt(req.params.orderCounter);
  console.log(orderCounter);
  const client = new MongoClient(url);
  try {
    await client.connect();
    const db = client.db(dbName);
    const result = await db
      .collection("orders")
      .deleteOne({ _id: orderCounter });    
    if (result.deletedCount === 1) {
      res.json({ message: "Order deleted successfully" });
    } else {
      res.status(404).json({ error: "Order not found" });
    }
  } catch (error) {
    console.error("Error:", error);
    res.status(500).json({ error: "An error occurred" });
  } finally {
    client.close();
  }
});

app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});
