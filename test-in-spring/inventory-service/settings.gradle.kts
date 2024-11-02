rootProject.name = "fastcampus-test-inventory"

plugins {
    id("de.fayard.refreshVersions") version "0.60.5"
}

include(
    "practices:simple",
    "practices:inventory"
)