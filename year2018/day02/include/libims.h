#pragma once

long long ims_get_list_checksum(const char *const *box_ids, size_t boxc);

const char *ims_get_common_letters_between_correct_boxes(const char *const *box_ids, size_t boxc);