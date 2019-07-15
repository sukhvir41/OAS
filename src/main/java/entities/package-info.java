@org.hibernate.annotations.GenericGenerator(
        name = "department_sequence",
        strategy = "enhanced-sequence",
        parameters = {
                @org.hibernate.annotations.Parameter(
                        name = "sequence_name",
                        value = "department_sequence"
                ),
                @org.hibernate.annotations.Parameter(
                        name = "initial_value",
                        value = "0"
                )
        }
)

@org.hibernate.annotations.GenericGenerator(
        name = "course_sequence",
        strategy = "enhanced-sequence",
        parameters = {
                @org.hibernate.annotations.Parameter(
                        name = "sequence_name",
                        value = "course_sequence"
                ),
                @org.hibernate.annotations.Parameter(
                        name = "initial_value",
                        value = "0"
                )
        }
)
@org.hibernate.annotations.GenericGenerator(
        name = "subject_sequence",
        strategy = "enhanced-sequence",
        parameters = {
                @org.hibernate.annotations.Parameter(
                        name = "sequence_name",
                        value = "subject_sequence"
                ),
                @org.hibernate.annotations.Parameter(
                        name = "initial_value",
                        value = "0"
                )
        }
)
@org.hibernate.annotations.GenericGenerator(
        name = "class_room_sequence",
        strategy = "enhanced-sequence",
        parameters = {
                @org.hibernate.annotations.Parameter(
                        name = "sequence_name",
                        value = "class_room_sequence"
                ),
                @org.hibernate.annotations.Parameter(
                        name = "initial_value",
                        value = "0"
                )
        }
)


package entities;

