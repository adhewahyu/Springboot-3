-- TABLE PERMISSION
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('6cf53724-cecb-4145-89d8-03ee33310694', 0, '', 'user', 'User Management root', 'User Management', '', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('09c14289-6b74-480d-adb3-4acffe27b6d5', 1, '', 'user', 'Create User', 'Create User', '6cf53724-cecb-4145-89d8-03ee33310694', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('70f365de-7109-4f45-a08a-d2d36c990df2', 1, '', 'user', 'Update User', 'Update User', '6cf53724-cecb-4145-89d8-03ee33310694', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('0f324f23-61c6-49d3-a3f6-edb224284437', 1, '', 'user', 'Delete User', 'Delete User', '6cf53724-cecb-4145-89d8-03ee33310694', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('d66be642-a30d-4f33-bd95-73cae189c57b', 1, '', 'user', 'View User', 'View User', '6cf53724-cecb-4145-89d8-03ee33310694', 1);