# Design Specification: Premium Aesthetics 🎨

The CoffeeShop Professional v2 uses a design language inspired by modern SaaS platforms (like Stripe or Linear) to provide a high-end, non-boxy user experience.

## 🎨 Color Palette

- **Primary (Indigo)**: `#6366F1` - Used for primary actions and brand presence.
- **Surface Dark**: `#0F172A` - Used for the sidebar and structural contrast.
- **Surface Light**: `#F8FAFC` - Main background for low eye strain.
- **Card Background**: `White` - Defines content boundaries without harsh borders.
- **Text Main**: `#1E2B3B` - High contrast for readability.
- **Text Sub**: `#64748B` - Lower contrast for labels and metadata.

## 📐 Shape & Geometry

- **Rounded Corners**: Standard radius of `15px` for cards and buttons.
- **Anti-Aliased Rendering**: All custom components use `RenderingHints.VALUE_ANTIALIAS_ON` for smooth, non-pixelated edges.
- **Implicit Grid**: Components are spaced using a `25px` or `40px` padding logic to create "white space" luxury.

## 🔘 Component Behaviors

- **Buttons**: Feature an instant color-shift on hover and a hand cursor.
- **Sidebar**: Uses bold white for active states and slate gray for inactive states to guide user focus.
- **KPI Cards**: Use vertical stacking (Label -> Value) to emphasize metrics.

## 🖋️ Typography

- **Primary Font**: `Inter` (if available) or `Sans-Serif`.
- **Dash Titles**: `28pt Bold`.
- **Body Text**: `14pt Regular`.
- **Metadata**: `12pt itals/slate`.
