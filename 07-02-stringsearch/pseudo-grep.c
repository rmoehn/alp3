#include <stdio.h>
#include <string.h>
#include <err.h>
#include "errors.h"

void pseudo_grep(const char *, int, FILE *);

int main(int argc, const char *argv[])
{
    // Check command line arguments
    if (argc == 2) {
        errx(ARG_ERROR, "There must be at least two command line arguments");
    }

    const char *pattern  = argv[1];
    int pat_length       = strlen(pattern);
        // Not quite from stdio.h, but not part of the task

    // Open the given file (further arguments are ignored)
    FILE *stream = fopen(argv[2], "r");
    if (stream == NULL) {
        err(ARG_ERROR, "Cannot open %s for reading", argv[2]);
    }

    // Search
    pseudo_grep(pattern, pat_length, stream);

    // Close the file
    if (fclose(stream) == EOF) {
        err(INPUT_ERROR, "Error closing file");
    }

    return 0;
}

/*
 * Prints every line of *stream containing pattern to stdout.
 */
void pseudo_grep(const char pattern[], int length, FILE *stream)
{
    /*
     * The stream is read character by character (byte by byte to be precise)
     * and compared to the first character of pattern. If the two characters
     * match, the next character read is compared to the pattern's second
     * character and so on.
     *     If there are length successive matches, the input
     * is wound back to the last newline and printed until the next newline.
     *     If there are less than length successive matches before a mismatch,
     * we start looking for pattern's first character again after the position
     * of the first match.
     *
     * The behaviour for patterns containing \n is undefined.
     */
    long offset       = 0;
    int matched_chars = 0;

    // Read the stream character by character
    int cur_char;
    while ((cur_char = fgetc(stream)) != EOF) {
        // Increment number of characters read since last \n
        ++offset;

        // If the current character matches the next character from pattern
        if (cur_char == pattern[matched_chars]) {
            // Increment the number of characters matched so far
            ++matched_chars;

            // If we have the whole pattern matched
            if (matched_chars == length) {
                // Print the offset of the match
                printf("%ld ", offset - (matched_chars - 1));
            }
        }
        // If we had a full match or a mismatch after a partial match
        else if (cur_char == pattern[matched_chars]
                 || (cur_char != pattern[matched_chars]
                     && matched_chars > 0)) {
            // Rewind to the position of the first matching character
            if (fseek(stream, -matched_chars, SEEK_CUR) == -1) {
                err(INPUT_ERROR, "Cannot go back in input stream");
            }

            // Reset number of characters matched and line offset
            offset        -= matched_chars;
            matched_chars = 0;
        }
    }
    if (ferror(stream)) {
        err(INPUT_ERROR, "Error in reading");
    }

    // Append newline to the list of offsets
    printf("\n");
}
