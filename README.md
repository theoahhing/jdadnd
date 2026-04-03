# JDA DnD Bot

A Discord-based Dungeons & Dragons game built using Java and the JDA (Java Discord API) library.

## 📌 Overview

This project is a personal learning and experimentation tool to:

* Refresh and improve Java skills
* Explore Discord bot development using JDA
* Build a modular, scalable DnD-style game system

## ⚙️ Features (Planned)

* 🎲 Dice rolling system (e.g. /roll 1d20)
* 🧙 Character creation and management
* ⚔️ Combat system
* 📜 Inventory system
* 🗺️ Campaign / session tracking
* 🤖 Discord slash commands via JDA

## 🏗️ Tech Stack

* Java (JDK 17+ recommended)
* JDA (Java Discord API)
* Maven or Gradle (TBD)

## 🚀 Getting Started

### Prerequisites

* Java JDK 17+
* Git
* Discord account + bot token

### Setup

1. Clone the repository:
   git clone https://github.com/<your-username>/jdadnd.git

2. Open the project in IntelliJ IDEA (or your IDE of choice)

3. Configure your bot token:

    * Create a `.env` file or config file (recommended)
    * Add your Discord bot token

4. Build and run the project

## 📁 Project Structure (Planned)

src/
├── main/
│   ├── bot/          # Bot entry point & listeners
│   ├── commands/     # Slash commands
│   ├── service/      # Game logic
│   ├── model/        # Game entities (Player, Character, etc.)
│   ├── util/         # Utilities
│   └── config/       # Configuration handling
└── test/             # Unit tests

## 🔐 Environment Variables

Do NOT commit sensitive data.

Example:
DISCORD_TOKEN=your_token_here

## 📚 Learning Goals

* Improve Java architecture and best practices
* Learn event-driven programming (JDA)
* Build a scalable game backend

## 📄 License

This project is currently unlicensed. You may add a license later (e.g. MIT).

---

## ✍️ Author
### BKTV Solutions
- Theophilus Ah-Hing (Electrical & Electronics Engineer)
- Reuben Koshy (Support Software Developer)
