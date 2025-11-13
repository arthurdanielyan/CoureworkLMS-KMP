package com.coursework.data

import com.coursework.data.bookDetails.model.NamedItemResponse
import com.coursework.domain.bookDetails.model.BookDetails
import com.coursework.domain.bookDetails.model.NamedItem
import com.coursework.domain.bookDetails.model.reviews.BookRatingDistribution
import com.coursework.domain.bookDetails.model.reviews.BookReview
import com.coursework.domain.books.model.books.Book
import kotlin.random.Random

object MockData {

    val books = listOf(
        Book(
            id = 1L,
            title = "Kotlin Programming: The Big Nerd Ranch Guide",
            subtitle = null,
            authors = listOf("Josh Skeen", "David Greenhalgh"),
            publisher = "Big Nerd Ranch",
            hasPdfVersion = true,
            rating = 5f,
        ),
        Book(
            id = 2L,
            title = "Android Programming: The Big Nerd Ranch Guide",
            subtitle = null,
            authors = listOf("Bill Phillips", "Chris Stewart", "Kristin Marsicano"),
            publisher = "Big Nerd Ranch",
            hasPdfVersion = true,
            rating = 4.7f
        ),
        Book(
            id = 3L,
            title = "Pro Android 12",
            subtitle = "Developing Modern Mobile Apps",
            authors = listOf("Dave MacLean", "Satya Komatineni", "Sanjay Singh"),
            publisher = "Apress",
            hasPdfVersion = false,
            rating = 4.5f
        ),
        Book(
            id = 4L,
            title = "Android Jetpack Compose Essentials",
            subtitle = null,
            authors = listOf("Neil Smyth"),
            publisher = "TechPress",
            hasPdfVersion = true,
            rating = 4.6f
        ),
        Book(
            id = 5L,
            title = "Programming Android",
            subtitle = "Kotlin Edition",
            authors = listOf("Brett McLaughlin"),
            publisher = "O'Reilly Media",
            hasPdfVersion = false,
            rating = 4.3f
        ),
        Book(
            id = 6L,
            title = "Head First Android Development",
            subtitle = "A Brain-Friendly Guide",
            authors = listOf("Dawn Griffiths", "David Griffiths"),
            publisher = "O'Reilly Media",
            hasPdfVersion = true,
            rating = 4.7f
        ),
        Book(
            id = 7L,
            title = "Effective Kotlin",
            subtitle = "Best Practices for Kotlin Development",
            authors = listOf("Marcin Moskala"),
            publisher = "Leanpub",
            hasPdfVersion = true,
            rating = 4.9f
        ),
        Book(
            id = 8L,
            title = "Kotlin Coroutines by Tutorials",
            subtitle = null,
            authors = listOf("Raywenderlich Tutorial Team"),
            publisher = "Raywenderlich",
            hasPdfVersion = true,
            rating = 4.6f
        ),
        Book(
            id = 9L,
            title = "Android Clean Code",
            subtitle = null,
            authors = listOf("Robert C. Martin", "Android Dev Team"),
            publisher = "Prentice Hall",
            hasPdfVersion = false,
            rating = 4.4f
        ),
        Book(
            id = 10L,
            title = "Mastering Android Development",
            subtitle = null,
            authors = listOf("John Horton"),
            publisher = "Packt Publishing",
            hasPdfVersion = true,
            rating = 4.5f
        )
    ) + (1..200).map {
        Book(
            id = it.toLong(),
            title = "Book $it",
            subtitle = "Subtitle $it".takeIf { Random.nextBoolean() },
            authors = listOf("Author $it"),
            publisher = "Publisher $it",
            hasPdfVersion = Random.nextBoolean(),
            rating = Random.nextDouble(0.0, 5.0).toFloat()  // convert to Float
        )
    }


    val bookDetails = listOf(
        BookDetails(
            id = 1L,
            description = "This comprehensive and beginner-friendly guide introduces readers to Kotlin programming with a focus on practical Android development. Written by Big Nerd Ranch instructors, it combines hands-on projects, exercises, and real-world examples to help developers transition from Java or start from scratch with confidence. Each chapter builds upon the last to gradually develop both language fluency and problem-solving skills essential for mobile development in today’s Kotlin-first world.",
            title = "Kotlin Programming: The Big Nerd Ranch Guide",
            subtitle = null,
            authors = listOf("Josh Skeen", "David Greenhalgh"),
            publisher = "Big Nerd Ranch",
            publicationYear = 2018,
            edition = "2nd",
            categories = listOf(
                NamedItem(id = 3, displayName = "Programming")
            ),
            hasPdfVersion = true,
            pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            pdfTitle = "Kotlin Programming Guide (PDF)",
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 10,
            copiesAvailable = 6,
            language = "English",
            isReferenceOnly = false,
            rating = 3.6f
        ),
        BookDetails(
            id = 2L,
            description = "This flagship guide from Big Nerd Ranch remains one of the most respected Android books in the industry. It walks readers through building fully functional Android apps using modern tools like Kotlin, Android Studio, and Jetpack libraries. Every topic—from layouts and navigation to architecture and testing—is presented through immersive examples that reflect real-world development workflows. Whether you're a student or a working developer, it’s the perfect foundation for mastering professional Android app creation.",
            title = "Android Programming: The Big Nerd Ranch Guide",
            subtitle = null,
            authors = listOf("Bill Phillips", "Chris Stewart", "Kristin Marsicano"),
            publisher = "Big Nerd Ranch",
            publicationYear = 2021,
            edition = "4th",
            categories = listOf(
                NamedItem(id = 3, displayName = "Programming"),
                NamedItem(id = 1, displayName = "Physics")
            ),
            hasPdfVersion = true,
            pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            pdfTitle = "Android Programming Guide (PDF)",
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 12,
            copiesAvailable = 4,
            language = "English",
            isReferenceOnly = false,
            rating = 5f
        ),
        BookDetails(
            id = 3L,
            description = "Pro Android 12 dives deep into the Android platform’s modern APIs and design philosophies, giving readers a professional-level understanding of app architecture, performance optimization, and system integration. It covers everything from Jetpack components and dependency injection to accessibility and testing, with special emphasis on adopting Android 12 features such as dynamic themes and privacy controls. Perfect for experienced developers seeking to refine their craft and write cleaner, more maintainable code for large-scale apps.",
            title = "Pro Android 12",
            subtitle = "Developing Modern Mobile Apps",
            authors = listOf("Dave MacLean", "Satya Komatineni", "Sanjay Singh"),
            publisher = "Apress",
            publicationYear = 2021,
            edition = "1st",
            categories = listOf(
                NamedItem(id = 3, displayName = "Programming"),
                NamedItem(id = 1, displayName = "Physics")
            ),
            hasPdfVersion = false,
            pdfUrl = null,
            pdfTitle = null,
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 8,
            copiesAvailable = 3,
            language = "English",
            isReferenceOnly = false,
            rating = 4.4f
        ),
        BookDetails(
            id = 4L,
            description = "This practical reference book focuses on Google’s modern UI toolkit, Jetpack Compose. Readers learn how to design, animate, and manage reactive UIs using declarative patterns that drastically reduce boilerplate and improve readability. The book explains key Compose concepts such as state management, recomposition, and interoperability with Views. By the end, developers will have the skills to create polished, scalable interfaces that adapt beautifully across screen sizes and devices—essential knowledge for the modern Android developer.",
            title = "Android Jetpack Compose Essentials",
            subtitle = null,
            authors = listOf("Neil Smyth"),
            publisher = "TechPress",
            publicationYear = 2022,
            edition = "1st",
            categories = listOf(
                NamedItem(id = 3, displayName = "Programming"),
                NamedItem(id = 1, displayName = "Physics")
            ),
            hasPdfVersion = true,
            pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            pdfTitle = "Jetpack Compose Essentials (PDF)",
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 15,
            copiesAvailable = 9,
            language = "English",
            isReferenceOnly = false,
            rating = 4.8f
        ),
        BookDetails(
            id = 5L,
            description = "Programming Android: Kotlin Edition is a deep dive into the Android ecosystem with an emphasis on modern Kotlin-first development. It blends the fundamentals of Android architecture with contemporary techniques like coroutines, Flow, and reactive design. The author skillfully balances theory and application, showing how to build efficient, well-structured apps while keeping code readable and scalable. Ideal for developers transitioning from Java or those who want a structured, no-nonsense guide to Android with Kotlin.",
            title = "Programming Android",
            subtitle = "Kotlin Edition",
            authors = listOf("Brett McLaughlin"),
            publisher = "O'Reilly Media",
            publicationYear = 2020,
            edition = "5th",
            categories = listOf(
                NamedItem(id = 3, displayName = "Programming"),
                NamedItem(id = 1, displayName = "Physics")
            ),
            hasPdfVersion = false,
            pdfUrl = null,
            pdfTitle = null,
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 7,
            copiesAvailable = 2,
            language = "English",
            isReferenceOnly = false,
            rating = 4.3f
        ),
        BookDetails(
            id = 6L,
            description = "Head First Android Development takes a visually rich, hands-on approach to teaching Android programming. Its quirky humor and brain-friendly teaching style help readers grasp complex topics like lifecycle management, data storage, networking, and UI design. Each chapter feels like a mini-project, encouraging active learning through puzzles, diagrams, and challenges. Whether you’re an absolute beginner or an experienced developer wanting to revisit fundamentals in an engaging format, this book makes Android development genuinely enjoyable.",
            title = "Head First Android Development",
            subtitle = "A Brain-Friendly Guide",
            authors = listOf("Dawn Griffiths", "David Griffiths"),
            publisher = "O'Reilly Media",
            publicationYear = 2021,
            edition = "3rd",
            categories = listOf(
                NamedItem(id = 3, displayName = "Programming"),
                NamedItem(id = 1, displayName = "Physics")
            ),
            hasPdfVersion = true,
            pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            pdfTitle = "Head First Android Dev (PDF)",
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 11,
            copiesAvailable = 5,
            language = "English",
            isReferenceOnly = false,
            rating = 4.5f
        ),
        BookDetails(
            id = 7L,
            description = "Effective Kotlin by Marcin Moskala is a masterpiece that distills years of Kotlin experience into a collection of best practices and idiomatic patterns. It teaches how to write safer, cleaner, and more expressive Kotlin code, focusing on immutability, performance, and readability. Each chapter explores a different principle with clear explanations and justifications. It’s not just a style guide—it’s a philosophy of good software craftsmanship that helps Kotlin developers mature into seasoned professionals.",
            title = "Effective Kotlin",
            subtitle = "Best Practices for Kotlin Development",
            authors = listOf("Marcin Moskala"),
            publisher = "Leanpub",
            publicationYear = 2019,
            edition = "1st",
            categories = listOf(
                NamedItem(id = 3, displayName = "Programming"),
                NamedItem(id = 1, displayName = "Physics")
            ),
            hasPdfVersion = true,
            pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            pdfTitle = "Effective Kotlin (PDF)",
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 6,
            copiesAvailable = 1,
            language = "English",
            isReferenceOnly = false,
            rating = 4.9f
        ),
        BookDetails(
            id = 8L,
            description = "Kotlin Coroutines by Tutorials provides an accessible and progressive learning path for mastering asynchronous programming in Kotlin. Written by the Raywenderlich team, it demystifies concurrency, explaining suspending functions, flows, structured concurrency, and coroutine scopes with plenty of practical examples. Each chapter builds upon the last, helping developers write cleaner, more efficient, and maintainable code for Android apps. It’s a must-read for anyone serious about mastering modern Kotlin development techniques.",
            title = "Kotlin Coroutines by Tutorials",
            subtitle = null,
            authors = listOf("Raywenderlich Tutorial Team"),
            publisher = "Raywenderlich",
            publicationYear = 2020,
            edition = "1st",
            categories = listOf(
                NamedItem(id = 3, displayName = "Programming"),
                NamedItem(id = 1, displayName = "Physics")
            ),
            hasPdfVersion = true,
            pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            pdfTitle = "Kotlin Coroutines (PDF)",
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 14,
            copiesAvailable = 10,
            language = "English",
            isReferenceOnly = false,
            rating = 4.6f
        ),
        BookDetails(
            id = 9L,
            description = "Android Clean Code brings the timeless principles of software craftsmanship into the Android ecosystem. Inspired by Robert C. Martin’s Clean Code philosophy, it focuses on writing maintainable, testable, and scalable Android applications. The authors address real-world architectural challenges, including dependency management, SOLID principles, and feature modularization. This book is perfect for developers who want to elevate their Android apps beyond ‘working code’ to code that’s elegant, reliable, and built to last.",
            title = "Android Clean Code",
            subtitle = null,
            authors = listOf("Robert C. Martin", "Android Dev Team"),
            publisher = "Prentice Hall",
            publicationYear = 2019,
            edition = "1st",
            categories = listOf(
                NamedItem(id = 3, displayName = "Programming"),
                NamedItem(id = 1, displayName = "Physics")
            ),
            hasPdfVersion = false,
            pdfUrl = null,
            pdfTitle = null,
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 9,
            copiesAvailable = 3,
            language = "English",
            isReferenceOnly = true,
            rating = 4.8f
        ),
        BookDetails(
            id = 10L,
            description = "Mastering Android Development is aimed at experienced developers who want to refine their understanding of Android architecture, performance tuning, and modern design practices. It goes beyond tutorials to explore dependency injection, reactive patterns, modularization, and integration with cloud backends. The book also covers advanced UI design, data synchronization, and testing strategies for professional-grade applications. Written with clarity and precision, it empowers developers to build complex apps that scale gracefully across devices and versions.",
            title = "Mastering Android Development",
            subtitle = null,
            authors = listOf("John Horton"),
            publisher = "Packt Publishing",
            publicationYear = 2022,
            edition = "2nd",
            categories = listOf(
                NamedItem(id = 3, displayName = "Programming"),
                NamedItem(id = 1, displayName = "Physics")
            ),
            hasPdfVersion = true,
            pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
            pdfTitle = "Mastering Android Dev (PDF)",
            coverImageUrl = "https://habrastorage.org/webt/to/vg/et/tovgetgbjadxe5fttu3sntdfci4.jpeg",
            totalCopies = 13,
            copiesAvailable = 6,
            language = "English",
            isReferenceOnly = false,
            rating = 4.5f
        )
    )


    val Categories = listOf(
        NamedItemResponse(
            id = 1,
            displayName = "Physics",
        ),
        NamedItemResponse(
            id = 2,
            displayName = "Analytic Geometry and Linear Algebra",
        ),
        NamedItemResponse(
            id = 3,
            displayName = "Programming",
        ),
        NamedItemResponse(
            id = 4,
            displayName = "Philosophy",
        ),
        NamedItemResponse(
            id = 5,
            displayName = "History",
        ),
        NamedItemResponse(
            id = 6,
            displayName = "Network",
        ),
    )
    val Languages = listOf(
        NamedItemResponse(
            id = 1,
            displayName = "Armenian",
        ),
        NamedItemResponse(
            id = 2,
            displayName = "Russian",
        ),
        NamedItemResponse(
            id = 3,
            displayName = "English",
        ),
    )
    val Teachers = listOf(
        NamedItemResponse(
            id = 1,
            displayName = "E. Hovhannisyan",
        ),
        NamedItemResponse(
            id = 2,
            displayName = "T. Ganovich",
        ),
        NamedItemResponse(
            id = 3,
            displayName = "Tomeyan",
        ),
        NamedItemResponse(
            id = 4,
            displayName = "A. Baghdasaryan",
        ),
        NamedItemResponse(
            id = 5,
            displayName = "L. Andreasyan",
        ),
        NamedItemResponse(
            id = 6,
            displayName = "E. Virabyan",
        ),
    )

    // Some sample names
    private val names = listOf(
        "Hakob Karapetyan", "Artur Danielyan", "Karen Hovhannisyan", "Lilit Harutyunyan",
        "Aram Vardanyan", "Sofia Grigoryan", "Tigran Mkrtchyan", "Narine Sargsyan"
    )

    // Some sample words to build reviews
    private val words = listOf(
        "amazing", "boring", "helpful", "confusing", "well-written", "detailed",
        "excellent", "good", "average", "interesting", "clear", "practical",
        "fun", "inspiring", "challenging", "informative", "engaging", "recommended"
    )

    // Function to generate a random review with 5–50 words
    fun generateRandomReview(): String {
        val wordCount = Random.nextInt(5, 51)
        return (1..wordCount).joinToString(" ") { words.random() }
    }

    // Generate 250 reviews
    val BookReviews = (1..250).map {
        BookReview(
            id = it.toLong(),
            username = names.random(),
            review = generateRandomReview(),
            rating = Random.nextInt(1, 5),
        )
    }

    val BookRatingDistribution =
        BookRatingDistribution(
            oneStar = 0.1f,
            twoStars = 0.05f,
            threeStars = 0.15f,
            fourStars = 0.25f,
            fiveStars = 0.45f,
        )
}