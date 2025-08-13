# ID Card Generator

## Project Description

A Java Swing application to generate ID cards with live preview, photo upload, department dropdown, dynamic address wrapping, organization logo, and export to PNG functionality.

## Features

* **Live Preview:** Updates Name, ID, Department, Address, and Blood Group in real-time.
* **Photo Upload:** Upload a photo to appear on the ID card.
* **Department Dropdown:** Predefined list of departments for consistent input.
* **Dynamic Address Wrapping:** Automatically wraps long addresses beside the photo.
* **Organization Logo & Name:** Displays a logo and organization name at the top.
* **Footer with Organization Address:** Center-aligned, dynamic wrapping, with bold "Address:" label.
* **Export to PNG:** Save the generated ID card after validating all fields.

## Technologies Used

* Java
* Swing UI (`JFrame`, `JPanel`, `JTextField`, `JComboBox`, `JButton`)
* `BufferedImage` & `Graphics2D` for rendering
* `ImageIO` for reading images and exporting PNG


## Optional Enhancements

* Add QR code generation for ID verification.
* Support for signature upload.
* Double-sided ID card preview.
* Customizable theme colors for card.
