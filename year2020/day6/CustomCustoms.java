/* Licensed under Apache-2.0 */
package advent.year2020.day6;

public class CustomCustoms {
    public static int getCountUniqueQuestions(String input) {
        String[] groups = input.split("\n\n");

        int count = 0;
        for (String group : groups) {
            count += getGroupCountUniqueQuestions(group);
        }

        return count;
    }

    public static int getGroupCountUniqueQuestions(String group) {
        String[] people = group.split("\n");
        boolean[] answers = new boolean[26];

        for (String person : people) {
            for (int i = 0; i < person.length(); i++) {
                int answerIndex = person.toLowerCase().charAt(i);

                answers[answerIndex - 97] = true;
            }
        }

        int count = 0;

        for (int i = 0; i < answers.length; i++) {
            if (answers[i]) {
                count++;
            }
        }

        return count;
    }

    public static int getCountAllYesQuestions(String input) {
        String[] groups = input.split("\n\n");

        int count = 0;
        for (String group : groups) {
            count += getGroupCountAllYesQuestions(group);
        }

        return count;
    }

    public static int getGroupCountAllYesQuestions(String group) {
        String[] people = group.split("\n");
        boolean[] answers = new boolean[26];

        for (int i = 0; i < answers.length; i++) {
            answers[i] = true;
        }

        for (String person : people) {
            boolean[] personAnswers = new boolean[26];

            for (int i = 0; i < person.length(); i++) {
                int answerIndex = person.toLowerCase().charAt(i);

                personAnswers[answerIndex - 97] = true;
            }

            for (int i = 0; i < personAnswers.length; i++) {
                answers[i] = answers[i] && personAnswers[i];
            }
        }

        int count = 0;

        for (int i = 0; i < answers.length; i++) {
            if (answers[i]) {
                count++;
            }
        }

        return count;
    }
}
