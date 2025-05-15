# 📦 Changelog - RotatingHeadsPlus

All notable changes to this project will be documented in this file.

---

## [1.0.0] – 2025-05-15

### ✨ Added
- Support for rotating player heads, armor stands, and living entities
- 3 animation types: circular, ping-pong, and static
- Command system with:
    - `/rhead create`
    - `/rhead remove`
    - `/rhead list`
    - `/rhead reload`
    - `/rhead stop`
    - `/rhead help`
- Language system with built-in `en_US.yml` and `es_ES.yml`
- Configurable speed, interval, and detection radius via `config.yml`
- Automatic loading of animation data from `/data/*.yml`
- Support for custom frame-based animations via `/animations/*.yml`
- PlaceholderAPI support (optional): `%rotatingheadsplus_count%`, `%rotatingheadsplus_enabled%`
- Example files auto-generated in `/examples/` on first load
- Modular file structure with full API separation

---

## 🔜 Upcoming

- GUI animation editor (create/edit frames in-game)
- Preview mode for animations in real time
- Persistent registry of custom head types
- Integration with HolographicDisplays & DecentHolograms
- Animation conditions & click actions for advanced interactivity
