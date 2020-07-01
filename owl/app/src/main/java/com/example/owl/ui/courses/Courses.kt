/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.owl.ui.courses

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.contentColor
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.state
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.owl.R
import com.example.owl.model.courses
import com.example.owl.model.topics
import com.example.owl.ui.theme.BlueTheme
import com.example.owl.ui.utils.InsetsAmbient
import com.example.owl.ui.utils.systemBarPadding

@Composable
fun Courses(selectCourse: (Long) -> Unit) {
    BlueTheme {
        val (selectedTab, setSelectedTab) = state { CourseTabs.FEATURED }
        val tabs = CourseTabs.values()
        Scaffold(
            backgroundColor = MaterialTheme.colors.primarySurface,
            bottomBar = {
                BottomNavigation(
                    modifier = Modifier.preferredHeight(56.dp + InsetsAmbient.current.bottom)
                ) {
                    tabs.forEach { tab ->
                        BottomNavigationItem(
                            icon = { Icon(vectorResource(tab.icon)) },
                            label = { Text(stringResource(tab.title).toUpperCase()) },
                            selected = tab == selectedTab,
                            onSelect = { setSelectedTab(tab) },
                            alwaysShowLabels = false,
                            selectedContentColor = MaterialTheme.colors.secondary,
                            unselectedContentColor = contentColor(),
                            modifier = Modifier.systemBarPadding(bottom = true)
                        )
                    }
                }
            }
        ) { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            when (selectedTab) {
                CourseTabs.MY_COURSES -> MyCourses(courses, selectCourse, modifier)
                CourseTabs.FEATURED -> FeaturedCourses(courses, selectCourse, modifier)
                CourseTabs.SEARCH -> SearchCourses(topics, modifier)
            }
        }
    }
}

@Composable
fun CoursesAppBar() {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier.preferredHeight(80.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(16.dp)
                .gravity(Alignment.CenterVertically),
            asset = vectorResource(id = R.drawable.ic_lockup_white)
        )
        IconButton(
            modifier = Modifier.gravity(Alignment.CenterVertically),
            onClick = { /* todo */ }
        ) {
            Icon(Icons.Filled.AccountCircle)
        }
    }
}

private enum class CourseTabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    MY_COURSES(R.string.my_courses, R.drawable.ic_grain),
    FEATURED(R.string.featured, R.drawable.ic_featured),
    SEARCH(R.string.search, R.drawable.ic_search)
}
