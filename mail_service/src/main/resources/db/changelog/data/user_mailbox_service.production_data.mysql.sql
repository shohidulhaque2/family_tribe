
INSERT INTO user_mailbox_service.mailbox (id, creation_time, user_uuid, version) VALUES (1, '2021-03-27 03:07:55', '13d993c2-d736-42ce-9926-ec944b02cbcc', 1);

INSERT INTO user_mailbox_service.mail (id, creation_time, from_user_uuid, message, uuid, version, seen, mail_type) VALUES (1, '2021-03-27 03:07:57', '308a0b0f-38f1-48f4-9031-80e2e1a1c301', 'Some Random Messge', 'e10fc759-3bb0-484c-b383-d69c138bc4e8', 0, false, 'InvitationMail');
INSERT INTO user_mailbox_service.mail (id, creation_time, from_user_uuid, message, uuid, version, seen, mail_type) VALUES (2, '2021-03-27 03:08:17', '7680272e-38a7-430f-b3ac-4be8d03ddfa9', 'Some Random Messge', '7bf5e5c3-5a35-4652-845d-025ea1897c09', 0, false, 'InvitationMail');
INSERT INTO user_mailbox_service.mail (id, creation_time, from_user_uuid, message, uuid, version, seen, mail_type) VALUES (3, '2021-03-27 03:08:21', '764eafcf-4ab8-43bb-a2d7-a6a678131f5c', 'Some Random Messge', '57dd56d3-ea20-4a0b-bb79-db4414610bd6', 0, false, 'InvitationMail');
INSERT INTO user_mailbox_service.mail (id, creation_time, from_user_uuid, message, uuid, version, seen, mail_type) VALUES (4, '2021-03-27 03:08:23', '4f80644e-0294-456d-9036-8213df6797af', 'Some Random Messge', '26f81256-8ac5-4ee7-bdb0-098a2d7c31b9', 0, false, 'InvitationMail');
INSERT INTO user_mailbox_service.mail (id, creation_time, from_user_uuid, message, uuid, version, seen, mail_type) VALUES (5, '2021-03-27 03:09:58', 'a898edf6-7530-4384-8428-68bed24cd8af', 'Some Random Messge', '1ca0a9fa-b5d2-4840-8624-45418b9607d5', 0, false, 'InvitationMail');
INSERT INTO user_mailbox_service.mail (id, creation_time, from_user_uuid, message, uuid, version, seen, mail_type) VALUES (6, '2021-03-27 03:10:00', '497e9232-6491-42eb-9645-f1e7904f19f4', 'Some Random Messge', '82bd399d-4f58-4754-ba4f-b7c18b143457', 0, false, 'InvitationMail');
INSERT INTO user_mailbox_service.mail (id, creation_time, from_user_uuid, message, uuid, version, seen, mail_type) VALUES (7, '2021-03-27 03:10:02', '4553f6b5-072c-425c-acab-2268bd67c9ea', 'Some Random Messge', 'f6b69aaa-1e69-43a6-aae5-c26ceec62aa6', 0, false, 'InvitationMail');
INSERT INTO user_mailbox_service.mail (id, creation_time, from_user_uuid, message, uuid, version, seen, mail_type) VALUES (8, '2021-03-27 03:10:05', '1ba37695-f208-4a1e-b46f-310f0d1f0d02', 'Some Random Messge', 'a5886944-34b4-42ef-af73-4635f1e7a144', 0, false, 'InvitationMail');

INSERT INTO user_mailbox_service.mailbox_mail (mailbox_id, mail_id) VALUES (1, 1);
