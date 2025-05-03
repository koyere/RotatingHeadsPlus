# 🎡 RotatingHeadsPlus

A lightweight and powerful Minecraft plugin that allows you to animate **heads**, **armor stands**, and **entities** using smooth, customizable rotations.

> ✅ No dependencies  
> ✅ Optimized for Paper 1.21.1+  
> 🌍 Multilingual support (`en_US`, `es_ES`)  
> ⚙️ Fully configurable via YAML and config.yml

---

## 📦 Features

- 🧠 **Smart animations** for:
    - Player heads placed as blocks
    - Armor stands
    - Any living entity (e.g. villagers, zombies)
- 🌀 Supports:
    - **Circular rotation**
    - **Ping-pong rotation**
    - **Static facing**
- 🗂️ Loads animations from YAML files in `/data`
- 🧾 `/examples/` folder included to guide users
- 🔁 In-game commands to create, remove and list rotating entities
- 🔧 Language support with built-in `en_US` and `es_ES`

---

## 📥 Installation

1. Download the latest `.jar` from the [Releases](https://github.com/koyere/RotatingHeadsPlus/releases) tab.
2. Drop the file into your server's `plugins/` folder.
3. Start the server.
4. Configuration files will be generated in:
   plugins/RotatingHeadsPlus/


---

## 🔧 Configuration

### `config.yml`

```yaml
language: en_US
defaultSpeed: 5.0
defaultInterval: 2
maxRemovalDistance: 3.0
debug: false
```

Language files

Available in lang/en_US.yml and lang/es_ES.yml.
You can create your own translations by copying and editing those files.

| Command         | Description                             | Permission                |
| --------------- | --------------------------------------- | ------------------------- |
| `/rhead create` | Start rotating the object you're facing | `rotatingheadsplus.use`   |
| `/rhead remove` | Remove nearest rotating object          | `rotatingheadsplus.use`   |
| `/rhead list`   | Show count of active rotations          | `rotatingheadsplus.use`   |
| `/rhead reload` | Reload data/config/lang files           | `rotatingheadsplus.admin` |
| `/rhead help`   | Show help menu                          | `rotatingheadsplus.use`   |

📁 Folder Structure
```yaml
plugins/
└── RotatingHeadsPlus/
├── config.yml
├── lang/
│   ├── en_US.yml
│   └── es_ES.yml
├── data/
│   └── <active animations>.yml
└── examples/
└── head_pingpong.yml
```
🧠 Developer Info
Java 21

Spigot / Paper 1.21.1+

No external dependencies

Clean separation between core classes, config, commands, and animation logic

🔒 License
This plugin is proprietary software.
All Rights Reserved © 2024–present Eduardo Escobar.

Unauthorized distribution, modification, or publication is strictly prohibited.

❤️ Credits
Developed by Koyere

Inspired by RotatingHeads2 by Gennario (used as a base for redesign)

📣 Support
For questions, bug reports or pre-sale support, join our Discord:

🔗 Koyere Dev Support https://discord.gg/xKUjn3EJzR