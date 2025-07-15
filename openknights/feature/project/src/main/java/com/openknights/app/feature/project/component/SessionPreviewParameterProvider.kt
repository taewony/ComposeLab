package com.openknights.app.feature.project.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.openknights.app.core.model.Room
import com.openknights.app.core.model.Session
import com.openknights.app.core.model.Speaker
import com.openknights.app.core.model.Tag
import java.time.LocalDateTime

internal class SessionPreviewParameterProvider : PreviewParameterProvider<Session> {
    override val values = sequenceOf(
        Session(
            // single speaker with out bookmark
            id = "1",
            title = "Jetpack Compose에 있는 것, 없는 것",
            content = "",
            speakers = listOf(
                Speaker(
                    name = "안성용",
                    title = "",
                    description = "안드로이드 개발자",
                    imageUrl = "https://picsum.photos/200",
                ),
            ),
            tags = listOf(
                Tag("효율적인 코드베이스")
            ),
            starts = LocalDateTime.of(2023, 9, 12, 16, 10, 0),
            ends = LocalDateTime.of(2023, 9, 12, 16, 45, 0),
            room = Room("TRACK1"),
            
        ),
        Session(
            // single speaker with bookmark
            id = "1",
            title = "Jetpack Compose에 있는 것, 없는 것",
            content = "",
            speakers = listOf(
                Speaker(
                    name = "안성용",
                    title = "",
                    description = "안드로이드 개발자",
                    imageUrl = "https://picsum.photos/200",
                ),
            ),
            tags = listOf(
                Tag("효율적인 코드베이스")
            ),
            starts = LocalDateTime.of(2023, 9, 12, 16, 10, 0),
            ends = LocalDateTime.of(2023, 9, 12, 16, 45, 0),
            room = Room("TRACK1"),
            
        ),
        Session(
            // multi speakers
            id = "1",
            title = "Jetpack Compose에 있는 것, 없는 것",
            content = "",
            speakers = listOf(
                Speaker(
                    name = "안성용",
                    title = "",
                    description = "안드로이드 개발자",
                    imageUrl = "https://picsum.photos/200",
                ),
                Speaker(
                    name = "안성용",
                    title = "",
                    description = "안드로이드 개발자",
                    imageUrl = "https://picsum.photos/200",
                ),
                Speaker(
                    name = "안성용",
                    title = "",
                    description = "안드로이드 개발자",
                    imageUrl = "https://picsum.photos/200",
                ),
            ),
            tags = listOf(
                Tag("효율적인 코드베이스"),
                Tag("효율적인 코드베이스"),
                Tag("효율적인 코드베이스")
            ),
            starts = LocalDateTime.of(2023, 9, 12, 16, 10, 0),
            ends = LocalDateTime.of(2023, 9, 12, 16, 45, 0),
            room = Room("TRACK1"),
            
        ),
    )
}
