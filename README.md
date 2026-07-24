# ResumeATS Pro

<div align="center">

![Java](https://img.shields.io/badge/Java-11+-orange?style=for-the-badge&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue?style=for-the-badge&logo=mysql)
![Swing](https://img.shields.io/badge/Java%20Swing-GUI%20App-green?style=for-the-badge)
![Maven](https://img.shields.io/badge/Apache%20Maven-Build%20Tool-red?style=for-the-badge&logo=apache-maven)

**A professional desktop application that analyzes resume compatibility with job descriptions using advanced ATS scoring algorithms.**

[Features](#-features) • [Installation](#-installation) • [Development](#-development)

</div>

## Overview

ResumeATS Pro is a sophisticated Java-based desktop application designed to help job seekers optimize their resumes for Applicant Tracking Systems (ATS). By analyzing keyword compatibility between resumes and job descriptions, it provides actionable insights to improve job application success rates.

---

## Features

###  Authentication & Security
- **Secure User Registration** - Create personalized accounts
- **Encrypted Login System** - Protect user credentials
- **Session Management** - Maintain user context throughout application

###  Resume Processing
- **File Upload Support** - Process text-based resumes (.txt)
- **Content Extraction** - Intelligent text parsing and analysis
- **Progress Tracking** - Visual indicators for file processing

###  ATS Analysis
- **Keyword Matching** - Advanced algorithm for skill matching
- **Score Calculation** - Comprehensive compatibility scoring
- **Detailed Analytics** - Statistical breakdown and insights
- **Improvement Suggestions** - Actionable recommendations


## Technology Stack

| Component | Technology |
|-----------|------------|
| **Backend** | Java 11, JDBC, MySQL |
| **Frontend** | Java Swing, FlatLaf |
| **Database** | MySQL 9.4+ |
| **Build Tool** | Apache Maven |
| **Architecture** | MVC Pattern |

---

## Installation

### Prerequisites

Ensure you have the following installed on your system:

- **Java Development Kit (JDK) 11** or higher
- **MySQL Server 8.0** or higher
- **Apache Maven 3.6** or higher

### Step-by-Step Setup

#### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/ResumeATS_Pro.git
cd ResumeATS_Pro
```

#### 2. Database Configuration
```sql
-- Create the application database
CREATE DATABASE resume_ats_pro;

-- Verify database creation
SHOW DATABASES;
```

#### 3. Application Configuration
Update database credentials in `src/main/java/com/example/pro/LoginPagePro.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/resume_ats_pro";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_mysql_password";  // Update this
```

#### 4. Build and Launch
```bash
# Clean and compile the project
mvn clean compile

# Run the application
mvn exec:java
```

---

## Usage Guide

### 1. Account Creation
- Launch the application
- Click **"Register"** to create a new account
- Choose a username (min. 3 characters) and password (min. 4 characters)
- Complete registration to access the main dashboard

### 2. Resume Upload
- Click **"Upload Resume"** button
- Select a `.txt` file containing your resume content
- Monitor progress via the visual indicator
- Review parsed content in the display area

### 3. Job Description Analysis
- Paste the target job description in the designated text area
- Ensure comprehensive inclusion of job requirements and skills
- The system automatically processes the content

### 4. ATS Score Generation
- Click **"Calculate ATS Score"** to initiate analysis
- View comprehensive results including:
  - Overall compatibility percentage
  - Keyword match statistics
  - Specific matching terms
  - Improvement recommendations

---

## ATS Scoring System

### Score Interpretation

| Score Range | Rating | Description |
|-------------|--------|-------------|
| 🟢 **80-100%** | Excellent | Strong alignment with job requirements |
| 🟡 **60-79%** | Good | Good match with minor improvements needed |
| 🟠 **40-59%** | Average | Moderate alignment, consider skill additions |
| 🔴 **0-39%** | Needs Work | Significant keyword gaps detected |

### Algorithm Details

```java
// Keyword Extraction Process
1. Text normalization and tokenization
2. Stop word filtering (common words removal)
3. Case-insensitive matching
4. Statistical analysis and scoring

// Scoring Formula
ATS Score = (Matching Keywords ÷ Total Job Keywords) × 100
```

---

## Database Schema

### Users Table
```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---



## Development

### Project Structure
```
ResumeATS_Pro/
├── src/main/java/com/example/pro/
│   ├── LoginPagePro.java      # Authentication & user management
│   └── ResumeATSPro.java      # Main application & ATS engine
├── pom.xml                    # Maven dependencies & build config
└── README.md
```

### Key Components

#### LoginPagePro.java
- User authentication and registration
- Database connection management
- Session initialization

#### ResumeATSPro.java
- File processing and content extraction
- ATS scoring algorithm implementation
- Results visualization and analytics

### Building from Source
```bash
# Clean build
mvn clean package

# Run tests
mvn test

# Create executable JAR
mvn package
```

---

##  Future Plans

### Phase 1: Enhanced File Support
- [ ] PDF document parsing
- [ ] Microsoft Word (.docx) support
- [ ] Auto-format detection

### Phase 2: Advanced Analytics
- [ ] AI-powered resume suggestions
- [ ] Skill gap analysis
- [ ] Industry-specific templates

### Phase 3: Platform Expansion
- [ ] Web application version
- [ ] Mobile companion app
- [ ] API for integration

---
