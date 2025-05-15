# 🎡 RotatingHeadsPlus
A lightweight and professional Minecraft plugin that brings life to your world by animating heads, armor stands, and entities using smooth and customizable rotations — all powered by YAML configuration.

* ✅ No dependencies required
* ✅ Supports Minecraft 1.14 – 1.21.5
* ✅ PlaceholderAPI support (optional)
* 🌍 Multilingual (en_US, es_ES)
* ⚙️ Fully configurable and extensible
* 🧠 Developer API available

## 📦 Features
### 🧠 Rotatable objects:

* Player heads placed as blocks

* Armor stands (any pose)

* Any valid living entity (e.g. Villagers, Zombies)

### 🌀 Supports 3 rotation types:

* Circular (continuous clockwise)

* Ping-pong (back and forth between angles)

* Static (fixed angle)

### 🔁 Load animations from:

* ```yamldata/*.yml``` → persistent placed objects

* ```yaml/animations/*.yml``` → frame-based YAML animations

### ✨ Create objects via in-game commands or config

### 🧾 Example files auto-generated for easy learning

### 🧩 PlaceholderAPI support:

```yaml%rotatingheadsplus_count%```

```yaml%rotatingheadsplus_enabled%```

### 🧰 Developer API to interact with animations

## ✅ Compatibility
* Minecraft versions: 1.14 to 1.21.5

* Servers: Spigot, Paper, Purpur

* Java: Built with Java 21

* No NMS or version-locked code

## 📥 Installation
1. Download the latest .jar from Releases.

2. Place it in your plugins/ folder.

3. Start your server.

4. Configuration, language, and example folders will be generated under:

```yaml/plugins/RotatingHeadsPlus/```

## 🧾 YAML Animation Examples
```yaml/data/``` folder

Used for persistent placed heads/entities (created via ```yaml/rhead create```):

```yaml
location:
world: world
x: 101
y: 64
z: 100
animation:
type: circular
startYaw: 0
speed: 5.0
interval: 2
```
/animations/ folder

Frame-based animation for entities, armorstands or heads:

```yamltype: armorstand
loop: true
interval: 10
location:
world: world
x: 100.5
y: 64.0
z: -23.5
frames:
- [0, 0]
- [90, 0]
- [180, 0]
- [270, 0]
```
  
Supported types: head, armorstand, entity

Optional: Add conditions, clickActions, etc. (API ready)

## ⚙️ Configuration (config.yml)

```yamllanguage: en_US
defaultSpeed: 5.0
defaultInterval: 2
maxRemovalDistance: 3.0
debug: false
```
Language files are located in lang/en_US.yml and lang/es_ES.yml.

## 🔧 Commands & Permissions
```yaml
Command	Description	Permission
/rhead create	Starts rotating the object you're looking at	rotatingheadsplus.use
/rhead remove	Removes the nearest rotating object	rotatingheadsplus.use
/rhead list	Shows the number of active rotating objects	rotatingheadsplus.use
/rhead reload	Reloads language, config, and data files	rotatingheadsplus.admin
/rhead stop	Stops a nearby rotating object	rotatingheadsplus.admin
/rhead help	Displays help menu	rotatingheadsplus.use
```

## 📁 Folder Structure

```kotlin
plugins/
└── RotatingHeadsPlus/
├── config.yml
├── lang/
│   ├── en_US.yml
│   └── es_ES.yml
├── data/
│   └── <registered object files>.yml
├── animations/
│   └── custom_animation.yml
└── examples/
└── head_circular.yml
└── head_pingpong.yml
└── armorstand_rotation.yml
└── entity_spin.yml
```

## 🧠 Developer Info
* Java 21, Maven build

* Clean API via RotatingHeadsAPI.java

* Supports adding/removing animations at runtime

* PlaceholderAPI expansion class: RotatingHeadsPlaceholder

* Plugin is modular: animations, config, lang, listeners, logic are separated

* No use of NMS or reflection — pure Spigot API

## 🔒 License
This plugin is proprietary software.

All Rights Reserved © 2024–present Eduardo Escobar.

Unauthorized distribution, modification or resale is strictly prohibited.

## ❤️ Credits
Developed by Koyere

Based on the original idea of RotatingHeads2 by Gennario
(This plugin was rewritten from scratch and significantly extended)

## 📣 Support
💬 For questions, bug reports or pre-sale inquiries:
Join our Discord server:

🔗 Koyere Dev Support