INSERT INTO chat_service.chat_space_user (id, creator_uuid, uuid, version) VALUES (1, '13d993c2-d736-42ce-9926-ec944b02cbcc', '25c2d3bf-7a72-4ad7-8a40-8cbd7526150c',1);
INSERT INTO chat_service.chat_space_user (id, creator_uuid, uuid, version) VALUES (2, '555593c2-d736-42ce-9926-ec944b02cbcc', '99993c2-d736-42ce-9926-ec944b02cbcc' ,1);

INSERT INTO chat_service.chat_space (id, name, version, uuid) VALUES (1, 'ayl', 1, '6ae2995b-7c8e-41d0-95af-a76fb27834f3');
INSERT INTO chat_service.chat_space (id, name, version, uuid) VALUES (2, 'cow', 1, 'cd1ad54e-27b9-4ad3-b3b6-0901f658918b');
INSERT INTO chat_service.chat_space (id, name, version, uuid) VALUES (3, 'dog', 1, 'b1c17f65-7a28-422c-a838-ae2c9ed1404b');
INSERT INTO chat_service.chat_space (id, name, version, uuid) VALUES (4, 'cat', 1, '89a8b3cf-bd0c-4376-97c7-42290ecc1c8c');
INSERT INTO chat_service.chat_space (id, name, version, uuid) VALUES (5, 'elephant', 1, '241288cd-cf48-43ce-801c-75925f04616b');
INSERT INTO chat_service.chat_space (id, name, version, uuid) VALUES (6, 'jake', 1, 'b322d338-bc04-4878-89c2-76bd1f31afd4');
INSERT INTO chat_service.chat_space (id, name, version, uuid) VALUES (7, 'birthdaypart', 1, '69edb928-2cbf-40c8-adf5-cb9c2dedb96e');
INSERT INTO chat_service.chat_space (id, name, version, uuid) VALUES (8, 'cake', 1, '454c7297-3691-443b-a7fe-17cff30a17db');
INSERT INTO chat_service.chat_space (id, name, version, uuid) VALUES (9, 'ken', 1, '15d2b84f-9f31-4c1f-b86c-742f24f5adf0');
INSERT INTO chat_service.chat_space (id, name, version, uuid) VALUES (10, 'ryu', 1, '8862e414-274c-43a1-bdeb-b6fe99250841');
INSERT INTO chat_service.chat_space (id, name, version, uuid) VALUES (11, 'KAC', 1, 'd4f8c19d-434c-4ba6-b48a-a95249330c29');
INSERT INTO chat_service.chat_space (id, name, version, uuid) VALUES (12, 'TELEPHIONE', 1, 'b1bd201d-aa45-4763-a741-441a8c45518d');
INSERT INTO chat_service.chat_space (id, name, version, uuid) VALUES (13, 'MMEMEM', 1, '9add72cf-b42b-4bbe-9ffd-8948b442d788');
INSERT INTO chat_service.chat_space (id, name, version, uuid) VALUES (14, 'k', 1, 'ca61e649-666c-4d11-bcb8-a9feb67cbe3f');
INSERT INTO chat_service.chat_space (id, name, version, uuid) VALUES (15, 'OOO', 1, '6855d291-b8b1-4ea4-9b49-c7c491d818a9');

INSERT INTO chat_service.chat_space_user_chat_space (chat_space_user_id, chat_space_id) VALUES (1, 1);
INSERT INTO chat_service.chat_space_user_chat_space (chat_space_user_id, chat_space_id) VALUES (1, 2);
INSERT INTO chat_service.chat_space_user_chat_space (chat_space_user_id, chat_space_id) VALUES (1, 3);
INSERT INTO chat_service.chat_space_user_chat_space (chat_space_user_id, chat_space_id) VALUES (1, 4);
INSERT INTO chat_service.chat_space_user_chat_space (chat_space_user_id, chat_space_id) VALUES (1, 5);
INSERT INTO chat_service.chat_space_user_chat_space (chat_space_user_id, chat_space_id) VALUES (1, 6);
INSERT INTO chat_service.chat_space_user_chat_space (chat_space_user_id, chat_space_id) VALUES (1, 7);
INSERT INTO chat_service.chat_space_user_chat_space (chat_space_user_id, chat_space_id) VALUES (1, 8);
INSERT INTO chat_service.chat_space_user_chat_space (chat_space_user_id, chat_space_id) VALUES (1, 9);
INSERT INTO chat_service.chat_space_user_chat_space (chat_space_user_id, chat_space_id) VALUES (1, 10);
INSERT INTO chat_service.chat_space_user_chat_space (chat_space_user_id, chat_space_id) VALUES (1, 11);
INSERT INTO chat_service.chat_space_user_chat_space (chat_space_user_id, chat_space_id) VALUES (1, 12);
INSERT INTO chat_service.chat_space_user_chat_space (chat_space_user_id, chat_space_id) VALUES (1, 13);
INSERT INTO chat_service.chat_space_user_chat_space (chat_space_user_id, chat_space_id) VALUES (1, 14);
INSERT INTO chat_service.chat_space_user_chat_space (chat_space_user_id, chat_space_id) VALUES (1, 15);

INSERT INTO chat_service.user_invitation(id, invited_user, uuid, creation_timestamp, expiration_timestamp, is_invitation_accepted, version, chat_space_id)
values (1, '555593c2-d736-42ce-9926-ec944b02cbcc', '85f02cec-14e1-4fc8-8f64-f790fa8452bc', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false, 1, 1);

INSERT INTO chat_service.member (id, member_uuid, uuid, chat_space_id) VALUES (1, '555593c2-d736-42ce-9926-ec944b02cbcc', RANDOM_UUID(), 1);

