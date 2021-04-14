INSERT INTO user_registration_service.person_registration (id, email, first_name,
                                                           identity_server_user_id, last_name,
                                                           nickname, uuid, version)
VALUES (1, 'email@email.com', 'shohidul', 'www.keycloak.org', 'haque', 'nickname1',
        'ee3c41a0-1d7a-4082-816a-5143884bd37d', 1);
INSERT INTO user_registration_service.person_registration (id, email, first_name,
                                                           identity_server_user_id, last_name,
                                                           nickname, uuid, version)
VALUES (2, '2email@email.com', '2shohidul', 'www.keycloak.org', '2haque', 'nickname2',
        '61bafd6a-7944-4ffb-bd51-51f6266396c8', 1);
INSERT INTO user_registration_service.person_registration (id, email, first_name,
                                                           identity_server_user_id, last_name,
                                                           nickname, uuid, version)
VALUES (3, '3email@email.com', '3shohidul', 'www.keycloak.org', '3haque', 'nickname3',
        'd0409567-666f-46b6-acf8-c788597dca16', 1);
INSERT INTO user_registration_service.person_registration (id, email, first_name,
                                                           identity_server_user_id, last_name,
                                                           nickname, uuid, version)
VALUES (4, '4email@email.com', '4shohidul', 'www.keycloak.org', '4haque', 'nickname4',
        'e0df5c62-81e7-46cb-bc6c-f5027146f823', 1);

INSERT INTO user_registration_service.person_verification_token (id, expiration_time, token,
                                                                 user_verification_link_uri, uuid,
                                                                 version, person_registration_id)
VALUES (1, '2021-04-05 21:41:09', '2b0a9a91-ea53-44fa-b88e-f2e81d20dccd',
        'bfda190e-acf9-477c-9a5d-c240e1f04558', '109095e3-4c2a-4153-aa79-68a653a6953c', 1, 1);
INSERT INTO user_registration_service.person_verification_token (id, expiration_time, token,
                                                                 user_verification_link_uri, uuid,
                                                                 version, person_registration_id)
VALUES (2, '2021-04-06 21:41:23', '2ada13bf-370c-4933-a967-c6051bd15280',
        'e7f1959e-f947-4d77-a67d-9a57362d892d', '5fede189-970c-4b8e-8007-612ad7d02c99', 1, 2);
INSERT INTO user_registration_service.person_verification_token (id, expiration_time, token,
                                                                 user_verification_link_uri, uuid,
                                                                 version, person_registration_id)
VALUES (3, '2021-04-06 21:41:26', 'a1aea031-bae2-46a7-b3dc-e7034b2eee91',
        '7eca56cb-9b63-4a3a-944d-990ba4308cd9', 'a21c24a7-961f-44ad-83cb-23983d5e16fa', 1, 3);
INSERT INTO user_registration_service.person_verification_token (id, expiration_time, token,
                                                                 user_verification_link_uri, uuid,
                                                                 version, person_registration_id)
VALUES (4, '2021-04-06 21:41:31', '23e6712f-0576-4803-9293-1ee2d93681a3',
        '9d825630-6e6f-432e-9b4a-58b6970f1d10', '61b39e60-5d6b-469c-aac8-0fcfad54cbb9', 1, 4);
